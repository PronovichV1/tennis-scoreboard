package dev.pronovich.tennis.scoreboard.dto.response;


public record MatchScoreResponseDto(PlayerScoreResponseDto firstPlayer, PlayerScoreResponseDto secondPlayer, String winnerName) {
}
