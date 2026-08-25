package dev.pronovich.tennis.scoreboard.domain;

import dev.pronovich.tennis.scoreboard.model.Player;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class PlayerScore {

    private Player player;
    private Points points;
    private Integer games;
    private Integer sets;
    private Integer tieBreakPoints;

    public PlayerScore(Player player) {
        this.player = player;
        points = Points.ZERO;
        games = 0;
        sets = 0;
        tieBreakPoints = null;
    }


}
