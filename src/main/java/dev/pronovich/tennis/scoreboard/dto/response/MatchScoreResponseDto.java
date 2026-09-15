package dev.pronovich.tennis.scoreboard.dto.response;


import dev.pronovich.tennis.scoreboard.domain.MatchScore;

public record MatchScoreResponseDto(PlayerScoreResponseDto firstPlayer, PlayerScoreResponseDto secondPlayer, String winnerName) {
}
