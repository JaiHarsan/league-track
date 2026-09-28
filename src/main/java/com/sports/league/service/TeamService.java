package com.sports.league.service;

import com.sports.league.entity.Fixture;
import com.sports.league.entity.StandingsEntry;
import com.sports.league.entity.Team;
import com.sports.league.exception.ResourceNotFoundException;
import com.sports.league.repository.FixtureRepository;
import com.sports.league.repository.LeagueMatchRepository;
import com.sports.league.repository.StandingsEntryRepository;
import com.sports.league.repository.TeamRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final StandingsEntryRepository standingsEntryRepository;
    private final FixtureRepository fixtureRepository;
    private final LeagueMatchRepository leagueMatchRepository;

    public TeamService(TeamRepository teamRepository,
                       StandingsEntryRepository standingsEntryRepository,
                       FixtureRepository fixtureRepository,
                       LeagueMatchRepository leagueMatchRepository) {
        this.teamRepository = teamRepository;
        this.standingsEntryRepository = standingsEntryRepository;
        this.fixtureRepository = fixtureRepository;
        this.leagueMatchRepository = leagueMatchRepository;
    }

    @Transactional
    public Team registerTeam(Team team) {
        Team savedTeam = teamRepository.save(team);

        // Initialize standings entry for the new team
        StandingsEntry entry = new StandingsEntry();
        entry.setTeam(savedTeam);
        entry.setPlayed(0);
        entry.setWins(0);
        entry.setDraws(0);
        entry.setLosses(0);
        entry.setPoints(0);
        standingsEntryRepository.save(entry);

        return savedTeam;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team getTeamById(Long id) {
        return teamRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + id));
    }

    @Transactional
    public void deleteTeam(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new ResourceNotFoundException("Team not found with id: " + id);
        }

        // 1. Delete associated fixtures & match results involving this team
        List<Fixture> fixtures = fixtureRepository.findByHomeTeamIdOrAwayTeamId(id, id);
        for (Fixture fixture : fixtures) {
            leagueMatchRepository.findByFixtureId(fixture.getId())
                .ifPresent(leagueMatchRepository::delete);
            fixtureRepository.delete(fixture);
        }

        // 2. Delete standings entry for this team
        standingsEntryRepository.findByTeamId(id).ifPresent(standingsEntryRepository::delete);

        // 3. Delete the team
        teamRepository.deleteById(id);
    }
}
