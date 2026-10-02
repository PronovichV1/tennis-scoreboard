package dev.pronovich.tennis.scoreboard.dto.response;

import dev.pronovich.tennis.scoreboard.domain.Points;

public record PlayerScoreResponseDto (String name, String points, int games, int sets, int tieBreakPoints){
}
