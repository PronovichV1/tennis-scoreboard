package dev.pronovich.tennis.scoreboard.domain.engine;

import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
import dev.pronovich.tennis.scoreboard.model.Player;

import java.util.Optional;

public class MatchEngine implements Engine {

    private final GameEngine gameEngine;
    private final SetEngine setEngine ;

    public MatchEngine(){
        this.gameEngine = new GameEngine();
        this.setEngine = new SetEngine();

    }


    @Override
    public Optional<Player> calculation(Player player, MatchScore matchScore) {

        Optional<Player> gameWinner = gameEngine.calculation(player, matchScore);
        Optional<Player> setWinner;

        if (gameWinner.isPresent()){
            setWinner = setEngine.calculation(player, matchScore);
        }

        if (matchScore.isMatchedFinished()){
            matchScore.assignTheWinner(player);
            return Optional.of(player);
        }
        return Optional.empty();

    }

}
