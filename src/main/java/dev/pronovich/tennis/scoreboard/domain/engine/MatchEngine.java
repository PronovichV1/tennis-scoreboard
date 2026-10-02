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

        /* Old approach
        if (matchScore.isTieBreak()) {
            tieBreak.calculation(concretePlayerScore.getPlayer(), matchScore);
        } else {
            gameEngine.calculation(concretePlayerScore.getPlayer(), matchScore);
        }

        if (!matchScore.isGameWon() || (matchScore.isTieBreak() && !matchScore.isTieBreakWon())){
            return;
        }

            concretePlayerScore.addGamesScore(); /// add points

            if  (concretePlayerScore.getGames() == 6 && rivalPlayerScore.getGames() == 6){ /// tie-break activation
                matchScore.activateTieBreak();
                return;
            }

            if (isPlayerWinSet(matchScore, concretePlayerScore)){ /// win set
                concretePlayerScore.addSetsScore();
                concretePlayerScore.resetGames();
                rivalPlayerScore.resetGames();
                matchScore.setTieBreakWon(false);
            }


            if (isPlayerWinMatch(player, matchScore)) { /// WINNER
                matchScore.assignTheWinner(concretePlayerScore.getPlayer());
            }
         */
    }

    /// base win
    private boolean isPlayerWinSet(MatchScore matchScore, PlayerScore concretePLayerScore) {
        if (concretePLayerScore.getGames() == 6 && matchScore.getAnotherPlayerScore(concretePLayerScore.getPlayer()).getGames() < 5){
            return true;
        }
        if (matchScore.isTieBreakWon()){
            return true;
        }
        return false;
    }

    /// best of 3
    /// Win a whole game
    public boolean isPlayerWinMatch(Player player, MatchScore matchScore) {
        return ((matchScore.getSpecificPlayerScore(player).getSets() - (matchScore.getAnotherPlayerScore(player).getSets()) >= 2));
    }
}
