package dev.pronovich.tennis.scoreboard.dto.request;


import java.util.UUID;

public record MatchUuidDto(UUID uuid) {

    public MatchUuidDto(UUID uuid){
        if (uuid == null){
            throw new IllegalArgumentException("Uuid cannot be empty");
        }
        this.uuid = uuid;
    }

}
