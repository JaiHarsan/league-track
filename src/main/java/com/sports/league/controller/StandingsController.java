package com.sports.league.controller;

import com.sports.league.entity.StandingsEntry;
import com.sports.league.service.StandingsService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/standings")
public class StandingsController {

    private final StandingsService standingsService;

    public StandingsController(StandingsService standingsService) {
        this.standingsService = standingsService;
    }

    @GetMapping
    public ResponseEntity<List<StandingsEntry>> getStandings() {
        return ResponseEntity.ok(standingsService.getStandings());
    }
}
