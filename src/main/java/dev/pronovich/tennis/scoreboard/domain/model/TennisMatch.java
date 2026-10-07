package dev.pronovich.tennis.scoreboard.domain.model;

import java.util.Objects;
import java.util.UUID;

public record TennisMatch(UUID uuid, TennisPlayer tennisPlayer1, TennisPlayer tennisPlayer2, TennisPlayer winner) {

    public TennisMatch {
        Objects.requireNonNull(uuid, "Tennis match required: uuid");
        Objects.requireNonNull(tennisPlayer1, "Tennis match required: first player");
        Objects.requireNonNull(tennisPlayer2, "Tennis match required: second player");
    }
}
