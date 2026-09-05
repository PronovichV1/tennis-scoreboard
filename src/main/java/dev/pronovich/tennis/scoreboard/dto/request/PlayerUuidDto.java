package dev.pronovich.tennis.scoreboard.dto.request;


import dev.pronovich.tennis.scoreboard.exception.InvalidFormatException;

public record PlayerUuidDto(String uuid) {

    public PlayerUuidDto(String uuid) {
        if (uuid.isBlank() || uuid.isEmpty()){
            throw new IllegalArgumentException("Player UUID cannot be null or empty");
        }
        this.uuid = uuid;
    }
}
