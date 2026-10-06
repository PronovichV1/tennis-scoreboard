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

        PlayerScore concretePlayerScore = matchScore.getSpecificPlayerScore(player);
        Optional<Player> gameWinner = gameEngine.calculation(concretePlayerScore.getPlayer(), matchScore);

        if (gameWinner.isPresent() || (matchScore.isTieBreak() && gameWinner.isEmpty())){
            setEngine.calculation(concretePlayerScore.getPlayer(), matchScore);
        }

        if (matchScore.isMatchedFinished()){
            matchScore.assignTheWinner(concretePlayerScore.getPlayer());
            return Optional.of(concretePlayerScore.getPlayer());
        }
        return Optional.empty();

    }

}
