package dev.pronovich.tennis.scoreboard.dto.request;

public record GenerateMatchDto(String firstPlayerName, String secondPlayerName) {

    public GenerateMatchDto(String firstPlayerName, String secondPlayerName) {
        if (firstPlayerName.isBlank() || firstPlayerName.isEmpty()){
            throw new IllegalArgumentException("First player name cannot be empty");
        }
        this.firstPlayerName = firstPlayerName;
        if (secondPlayerName.isBlank() || secondPlayerName.isEmpty()){
            throw new IllegalArgumentException("Second player name cannot be empty");
        }
        this.secondPlayerName = secondPlayerName;
    }
}
