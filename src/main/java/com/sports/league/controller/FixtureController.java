package com.sports.league.controller;

import com.sports.league.entity.Fixture;
import com.sports.league.service.FixtureService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fixtures")
public class FixtureController {

    private final FixtureService fixtureService;

    public FixtureController(FixtureService fixtureService) {
        this.fixtureService = fixtureService;
    }

    @PostMapping("/generate")
    public ResponseEntity<List<Fixture>> generateFixtures() {
        return ResponseEntity.ok(fixtureService.generateFixtures());
    }

    @GetMapping
    public ResponseEntity<List<Fixture>> getAllFixtures() {
        return ResponseEntity.ok(fixtureService.getAllFixtures());
    }
}
