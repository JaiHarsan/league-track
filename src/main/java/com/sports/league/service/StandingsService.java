package com.sports.league.service;

import com.sports.league.entity.StandingsEntry;
import com.sports.league.entity.Team;
import com.sports.league.exception.ResourceNotFoundException;
import com.sports.league.repository.StandingsEntryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StandingsService {

    private final StandingsEntryRepository standingsEntryRepository;

    @Value("${league.points.win:3}")
    private int winPoints;

    @Value("${league.points.draw:1}")
    private int drawPoints;

    @Value("${league.points.loss:0}")
    private int lossPoints;

    public StandingsService(StandingsEntryRepository standingsEntryRepository) {
        this.standingsEntryRepository = standingsEntryRepository;
    }

    @Transactional
    public StandingsEntry createStandingsEntry(Team team) {
        StandingsEntry entry = new StandingsEntry();
        entry.setTeam(team);
        entry.setPlayed(0);
        entry.setWins(0);
        entry.setDraws(0);
        entry.setLosses(0);
        entry.setPoints(0);
        return standingsEntryRepository.save(entry);
    }

    @Transactional
    public void updateStandingsAfterResult(Team homeTeam, Team awayTeam, int homeScore, int awayScore) {
        StandingsEntry homeEntry = standingsEntryRepository.findByTeam(homeTeam)
            .orElseThrow(() -> new ResourceNotFoundException("Standings entry not found for team: " + homeTeam.getName()));
        StandingsEntry awayEntry = standingsEntryRepository.findByTeam(awayTeam)
            .orElseThrow(() -> new ResourceNotFoundException("Standings entry not found for team: " + awayTeam.getName()));

        homeEntry.setPlayed(homeEntry.getPlayed() + 1);
        awayEntry.setPlayed(awayEntry.getPlayed() + 1);

        if (homeScore > awayScore) {
            homeEntry.setWins(homeEntry.getWins() + 1);
            homeEntry.setPoints(homeEntry.getPoints() + winPoints);

            awayEntry.setLosses(awayEntry.getLosses() + 1);
            awayEntry.setPoints(awayEntry.getPoints() + lossPoints);
        } else if (awayScore > homeScore) {
            awayEntry.setWins(awayEntry.getWins() + 1);
            awayEntry.setPoints(awayEntry.getPoints() + winPoints);

            homeEntry.setLosses(homeEntry.getLosses() + 1);
            homeEntry.setPoints(homeEntry.getPoints() + lossPoints);
        } else {
            homeEntry.setDraws(homeEntry.getDraws() + 1);
            homeEntry.setPoints(homeEntry.getPoints() + drawPoints);

            awayEntry.setDraws(awayEntry.getDraws() + 1);
            awayEntry.setPoints(awayEntry.getPoints() + drawPoints);
        }

        standingsEntryRepository.save(homeEntry);
        standingsEntryRepository.save(awayEntry);
    }

    public List<StandingsEntry> getStandings() {
        return standingsEntryRepository.findAllSorted();
    }
}
