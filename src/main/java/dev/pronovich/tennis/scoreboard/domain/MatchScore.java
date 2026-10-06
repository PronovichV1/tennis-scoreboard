package dev.pronovich.tennis.scoreboard.domain;


import dev.pronovich.tennis.scoreboard.domain.model.TennisPlayer;
import dev.pronovich.tennis.scoreboard.exception.PlayerNotExist;
import dev.pronovich.tennis.scoreboard.model.Player;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;
import java.util.UUID;


@Getter
@Setter

public class MatchScore {

    private UUID uuid;
    private PlayerScore playerScore1;
    private PlayerScore playerScore2;
    private TennisPlayer winner;


    public MatchScore(PlayerScore playerScore1, PlayerScore playerScore2) {
        uuid = UUID.randomUUID();
        this.playerScore1 = playerScore1;
        this.playerScore2 = playerScore2;
        winner = null;
    }

    public void activateTieBreak(){
        getPlayerScore1().setTieBreakPoints(0);
        getPlayerScore2().setTieBreakPoints(0);
    }

    public void deactivateTieBreak() {
        getPlayerScore1().setTieBreakPoints(null);
        getPlayerScore2().setTieBreakPoints(null);
    }


    public PlayerScore getSpecificPlayerScore(Player player) {
        /// if someone pass
        if (playerScore1.getPlayer().equals(player)){
            return playerScore1;
        } else {
            return playerScore2;
        }
    }

    public PlayerScore getAnotherPlayerScore(Player player) {
        if (playerScore1.getPlayer().equals(player)) {
            return playerScore2;
        } else {
            return playerScore1;
        }
    }


    public boolean isTieBreak(){


        return playerScore1.getTieBreakPoints() != null && playerScore2.getTieBreakPoints() != null;
    }


    public TennisPlayer getTennisPlayerBy(String name) {
        String polishedName = name.toLowerCase(Locale.ROOT).trim();
        String firstPlayerName = playerScore1.getPlayer().name().toLowerCase(Locale.ROOT).trim();
        String secondPlayerName = playerScore2.getPlayer().name().toLowerCase(Locale.ROOT).trim();
        if (firstPlayerName.equals(polishedName)){
            return playerScore1.getPlayer();
        }
        if (secondPlayerName.equals(polishedName)){
            return playerScore2.getPlayer();
        }
        throw  new PlayerNotExist("Player with this uuid is not exist");
    }

    public void assignTheWinner(TennisPlayer player){
        winner = player;
    }


    public boolean isMatchedFinished() {
        return playerScore1.getSets() == 2 || playerScore2.getSets() == 2;
    }
}
