package com.sports.league.controller;

import com.sports.league.entity.LeagueMatch;
import com.sports.league.service.MatchService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping("/{id}/result")
    public ResponseEntity<LeagueMatch> recordResult(
            @PathVariable Long id,
            @Valid @RequestBody MatchResultRequest request) {
        LeagueMatch match = matchService.recordResult(id, request.getHomeScore(), request.getAwayScore());
        return ResponseEntity.ok(match);
    }

    public static class MatchResultRequest {

        @NotNull(message = "Home score is required")
        @PositiveOrZero(message = "Home score cannot be negative")
        private Integer homeScore;

        @NotNull(message = "Away score is required")
        @PositiveOrZero(message = "Away score cannot be negative")
        private Integer awayScore;

        public MatchResultRequest() {
        }

        public MatchResultRequest(Integer homeScore, Integer awayScore) {
            this.homeScore = homeScore;
            this.awayScore = awayScore;
        }

        public Integer getHomeScore() {
            return homeScore;
        }

        public void setHomeScore(Integer homeScore) {
            this.homeScore = homeScore;
        }

        public Integer getAwayScore() {
            return awayScore;
        }

        public void setAwayScore(Integer awayScore) {
            this.awayScore = awayScore;
        }
    }
}
