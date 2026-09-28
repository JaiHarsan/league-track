package com.sports.league.repository;

import com.sports.league.entity.StandingsEntry;
import com.sports.league.entity.Team;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StandingsEntryRepository extends JpaRepository<StandingsEntry, Long> {

    Optional<StandingsEntry> findByTeam(Team team);

    Optional<StandingsEntry> findByTeamId(Long teamId);

    @Query("SELECT s FROM StandingsEntry s ORDER BY s.points DESC, s.wins DESC, s.team.name ASC")
    List<StandingsEntry> findAllSorted();
}
