package com.sports.league.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "league_matches")
public class LeagueMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "fixture_id", nullable = false)
    @JsonIgnoreProperties("match")
    private Fixture fixture;

    @PositiveOrZero(message = "Home score cannot be negative")
    private Integer homeScore;

    @PositiveOrZero(message = "Away score cannot be negative")
    private Integer awayScore;

    private String winner;

    private boolean completed = false;

    public LeagueMatch() {
    }

    public LeagueMatch(Fixture fixture, Integer homeScore, Integer awayScore, String winner, boolean completed) {
        this.fixture = fixture;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.winner = winner;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fixture getFixture() {
        return fixture;
    }

    public void setFixture(Fixture fixture) {
        this.fixture = fixture;
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

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
