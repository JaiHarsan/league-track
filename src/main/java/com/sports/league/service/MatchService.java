package com.sports.league.service;

import com.sports.league.entity.Fixture;
import com.sports.league.entity.LeagueMatch;
import com.sports.league.entity.Team;
import com.sports.league.exception.InvalidMatchResultException;
import com.sports.league.exception.ResourceNotFoundException;
import com.sports.league.repository.FixtureRepository;
import com.sports.league.repository.LeagueMatchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchService {

    private final LeagueMatchRepository leagueMatchRepository;
    private final FixtureRepository fixtureRepository;
    private final StandingsService standingsService;

    public MatchService(LeagueMatchRepository leagueMatchRepository,
                        FixtureRepository fixtureRepository,
                        StandingsService standingsService) {
        this.leagueMatchRepository = leagueMatchRepository;
        this.fixtureRepository = fixtureRepository;
        this.standingsService = standingsService;
    }

    @Transactional
    public LeagueMatch recordResult(Long id, Integer homeScore, Integer awayScore) {
        if (homeScore == null || awayScore == null) {
            throw new InvalidMatchResultException("Scores must not be null");
        }
        if (homeScore < 0 || awayScore < 0) {
            throw new InvalidMatchResultException("Scores cannot be negative");
        }

        // Try finding by LeagueMatch ID first, then by Fixture ID
        LeagueMatch match = leagueMatchRepository.findById(id)
            .orElseGet(() -> leagueMatchRepository.findByFixtureId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found with id: " + id)));

        // CRITICAL DUPLICATE PROTECTION CHECK
        if (match.isCompleted()) {
            throw new InvalidMatchResultException("Match result has already been recorded");
        }

        Fixture fixture = match.getFixture();
        Team homeTeam = fixture.getHomeTeam();
        Team awayTeam = fixture.getAwayTeam();

        // Determine Winner
        String winner;
        if (homeScore > awayScore) {
            winner = homeTeam.getName();
        } else if (awayScore > homeScore) {
            winner = awayTeam.getName();
        } else {
            winner = "DRAW";
        }

        match.setHomeScore(homeScore);
        match.setAwayScore(awayScore);
        match.setWinner(winner);
        match.setCompleted(true);

        fixture.setStatus("COMPLETED");
        fixtureRepository.save(fixture);

        // Update Standings (Wins/Draws/Losses & Points)
        standingsService.updateStandingsAfterResult(homeTeam, awayTeam, homeScore, awayScore);

        return leagueMatchRepository.save(match);
    }
}
