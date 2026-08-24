package dev.pronovich.tennis.scoreboard.domain;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class MatchScore {

    public MatchScore(PlayerScore playerScore1, PlayerScore playerScore2) {
        this.playerScore1 = playerScore1;
        this.playerScore2 = playerScore2;
        winnerName = null;
    }

    private PlayerScore playerScore1;
    private PlayerScore playerScore2;
    private String winnerName;




}
