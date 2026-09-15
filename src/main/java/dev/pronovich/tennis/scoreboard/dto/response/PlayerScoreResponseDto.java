package dev.pronovich.tennis.scoreboard.dto.response;

import dev.pronovich.tennis.scoreboard.domain.Points;

public record PlayerScoreResponseDto (String name, String points, Integer games, Integer sets, Integer tieBreakPoints){
    @Override
    public String toString() {
        return "PlayerScoreResponseDto{" +
                "name='" + name + '\'' +
                ", points='" + points + '\'' +
                ", games=" + games +
                ", sets=" + sets +
                ", tieBreakPoints=" + tieBreakPoints +
                '}';
    }
}
