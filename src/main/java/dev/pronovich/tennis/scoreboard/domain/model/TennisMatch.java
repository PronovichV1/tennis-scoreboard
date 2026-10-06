package dev.pronovich.tennis.scoreboard.domain.model;

import java.util.UUID;

public record TennisMatch(UUID uuid, TennisPlayer tennisPlayer1, TennisPlayer tennisPlayer2) {
}
