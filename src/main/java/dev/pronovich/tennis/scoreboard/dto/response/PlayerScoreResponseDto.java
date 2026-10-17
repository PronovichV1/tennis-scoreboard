package dev.pronovich.tennis.scoreboard.dto.response;

public record PlayerScoreResponseDto (String name, String points, Integer games, Integer sets, Integer tieBreakPoints){
}
