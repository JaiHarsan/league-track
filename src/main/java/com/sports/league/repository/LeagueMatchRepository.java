package com.sports.league.repository;

import com.sports.league.entity.LeagueMatch;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueMatchRepository extends JpaRepository<LeagueMatch, Long> {
    Optional<LeagueMatch> findByFixtureId(Long fixtureId);
}
