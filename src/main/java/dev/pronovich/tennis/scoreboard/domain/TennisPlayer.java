package dev.pronovich.tennis.scoreboard.domain;

import java.util.Objects;
import java.util.UUID;

public record TennisPlayer(UUID uuid, String name) {
    public TennisPlayer{
        Objects.requireNonNull(name, "Player name cannot be null");
    }
}
