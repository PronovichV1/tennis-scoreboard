package dev.pronovich.tennis.scoreboard.domain.engine;

import dev.pronovich.tennis.scoreboard.domain.TennisMatch;
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
    public Optional<Player> calculation(Player player, TennisMatch tennisMatch) {

        PlayerScore concretePlayerScore = tennisMatch.getSpecificPlayerScore(player);
        Optional<Player> gameWinner = gameEngine.calculation(concretePlayerScore.getPlayer(), tennisMatch);

        if (gameWinner.isPresent() || (tennisMatch.isTieBreak() && gameWinner.isEmpty())){
            setEngine.calculation(concretePlayerScore.getPlayer(), tennisMatch);
        }

        if (tennisMatch.isMatchedFinished()){
            tennisMatch.assignTheWinner(concretePlayerScore.getPlayer());
            return Optional.of(concretePlayerScore.getPlayer());
        }
        return Optional.empty();

    }

}
