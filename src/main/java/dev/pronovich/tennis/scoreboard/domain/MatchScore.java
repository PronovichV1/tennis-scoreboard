package dev.pronovich.tennis.scoreboard.domain;


import dev.pronovich.tennis.scoreboard.model.Player;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class MatchScore {

    private PlayerScore playerScore1;
    private PlayerScore playerScore2;
    private String winnerName;

    public MatchScore(PlayerScore playerScore1, PlayerScore playerScore2) {
        this.playerScore1 = playerScore1;
        this.playerScore2 = playerScore2;
        winnerName = null;
    }


    public PlayerScore getSpecificPlayerScore(Player player) {
        if (playerScore1.getPlayer().getName().equals(player.getName())){
            return playerScore1;
        } else {
            return playerScore2;
        }
    }

    public PlayerScore getAnotherPlayerScore(Player player) {
        if (playerScore1.getPlayer().getName().equals(player.getName())) {
            return playerScore2;
        } else {
            return playerScore1;
        }
    }

    public void assignTheWinner(Player player){
        winnerName = player.getName();
    }

    
}
