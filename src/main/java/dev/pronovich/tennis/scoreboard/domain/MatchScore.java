package dev.pronovich.tennis.scoreboard.domain;


import dev.pronovich.tennis.scoreboard.model.Player;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;


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

    public void assignTheWinner(Player player){
        winnerName = player.getName();
    }


    public Optional<UUID> getUuidBy(String name) {
        String polishedName = name.toLowerCase(Locale.ROOT).trim();
        if (playerScore1.getPlayer().getName().equals(name)){
            return Optional.of(playerScore1.getPlayer().getUuid());
        }
        if (playerScore2.getPlayer().getName().equals(name)){
            return Optional.of(playerScore2.getPlayer().getUuid());
        }
        return Optional.empty();
    }
}
