package com.sports.league.service;

import com.sports.league.entity.Fixture;
import com.sports.league.entity.LeagueMatch;
import com.sports.league.entity.Team;
import com.sports.league.exception.InvalidMatchResultException;
import com.sports.league.repository.FixtureRepository;
import com.sports.league.repository.TeamRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FixtureService {

    private final TeamRepository teamRepository;
    private final FixtureRepository fixtureRepository;

    public FixtureService(TeamRepository teamRepository, FixtureRepository fixtureRepository) {
        this.teamRepository = teamRepository;
        this.fixtureRepository = fixtureRepository;
    }

    @Transactional
    public List<Fixture> generateFixtures() {
        List<Team> teams = teamRepository.findAll();
        if (teams.size() < 2) {
            throw new InvalidMatchResultException("At least 2 teams are required to generate fixtures");
        }

        if (fixtureRepository.count() > 0) {
            throw new InvalidMatchResultException("Fixtures have already been generated");
        }

        List<Fixture> fixtures = new ArrayList<>();
        LocalDateTime baseDate = LocalDateTime.now();

        int matchCount = 0;
        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                matchCount++;
                Fixture fixture = new Fixture();
                fixture.setHomeTeam(teams.get(i));
                fixture.setAwayTeam(teams.get(j));
                fixture.setMatchDate(baseDate.plusDays(matchCount));
                fixture.setStatus("SCHEDULED");

                LeagueMatch match = new LeagueMatch();
                match.setFixture(fixture);
                match.setCompleted(false);
                fixture.setMatch(match);

                fixtures.add(fixture);
            }
        }

        return fixtureRepository.saveAll(fixtures);
    }

    public List<Fixture> getAllFixtures() {
        return fixtureRepository.findAll();
    }
}
