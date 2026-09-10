package dev.pronovich.tennis.scoreboard.dto.response;

import dev.pronovich.tennis.scoreboard.model.Player;

public record MatchResponseDto(Integer id, Player player1, Player player2, Player winner) {
}
