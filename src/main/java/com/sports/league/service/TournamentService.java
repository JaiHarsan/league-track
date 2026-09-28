package com.sports.league.service;

import com.sports.league.repository.FixtureRepository;
import com.sports.league.repository.LeagueMatchRepository;
import com.sports.league.repository.StandingsEntryRepository;
import com.sports.league.repository.TeamRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TournamentService {

    private final LeagueMatchRepository leagueMatchRepository;
    private final FixtureRepository fixtureRepository;
    private final StandingsEntryRepository standingsEntryRepository;
    private final TeamRepository teamRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public TournamentService(LeagueMatchRepository leagueMatchRepository,
                             FixtureRepository fixtureRepository,
                             StandingsEntryRepository standingsEntryRepository,
                             TeamRepository teamRepository) {
        this.leagueMatchRepository = leagueMatchRepository;
        this.fixtureRepository = fixtureRepository;
        this.standingsEntryRepository = standingsEntryRepository;
        this.teamRepository = teamRepository;
    }

    @Transactional
    public void resetTournament() {
        // Deletion in correct dependency order
        leagueMatchRepository.deleteAllInBatch();
        fixtureRepository.deleteAllInBatch();
        standingsEntryRepository.deleteAllInBatch();
        teamRepository.deleteAllInBatch();

        // Reset MySQL AUTO_INCREMENT counters to 1
        entityManager.createNativeQuery("ALTER TABLE league_matches AUTO_INCREMENT = 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE fixtures AUTO_INCREMENT = 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE standings_entries AUTO_INCREMENT = 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE teams AUTO_INCREMENT = 1").executeUpdate();
    }
}
