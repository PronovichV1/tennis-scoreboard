package dev.pronovich.tennis.scoreboard.domain;

import dev.pronovich.tennis.scoreboard.model.Player;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter

public class MatchScore {

    private Player player1;
    private PlayerScore playerScore1;
    private Player player2;
    private PlayerScore playerScore2;
    private String winnerName;

    public MatchScore(Player player1, PlayerScore playerScore1, Player player2, PlayerScore playerScore2) {
        this.player1 = player1;
        this.playerScore1 = playerScore1;
        this.player2 = player2;
        this.playerScore2 = playerScore2;
        winnerName = null;
    }


}
