package dev.pronovich.tennis.scoreboard.dto.response;

import dev.pronovich.tennis.scoreboard.domain.PlayerScore;

public record TwoPlayerScoresDto(PlayerScore playerScore1, PlayerScore playerScore2) {
}
