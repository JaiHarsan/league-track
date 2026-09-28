package com.sports.league.controller;

import com.sports.league.service.TournamentService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tournament")
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @DeleteMapping("/reset")
    public ResponseEntity<Map<String, String>> resetTournament() {
        tournamentService.resetTournament();
        Map<String, String> response = new HashMap<>();
        response.put("message", "Tournament reset successfully");
        return ResponseEntity.ok(response);
    }
}
