package dev.pronovich.tennis.scoreboard.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class PlayerScore {

    private Integer points;
    private Integer games;
    private Integer sets;
    private Integer tieBreakPoints;

    public PlayerScore() {
        points = 0;
        games = 0;
        sets = 0;
        tieBreakPoints = null;
    }


}
