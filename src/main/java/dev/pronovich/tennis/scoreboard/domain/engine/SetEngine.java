package dev.pronovich.tennis.scoreboard.domain.engine;

import dev.pronovich.tennis.scoreboard.domain.TennisMatch;
import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
import dev.pronovich.tennis.scoreboard.model.Player;

import java.util.Optional;


public class SetEngine implements Engine {

    private final TieBreakEngine tieBreakEngine;
    public SetEngine() {
        tieBreakEngine = new TieBreakEngine();
    }

    @Override
    public Optional<Player> calculation(Player player, TennisMatch tennisMatch) {

        PlayerScore concretePlayerScore = tennisMatch.getSpecificPlayerScore(player);
        PlayerScore rivalPlayerScore = tennisMatch.getAnotherPlayerScore(player);
        /// Is it TieBreak is active?
        if (tennisMatch.isTieBreak()){
           Optional<Player> tieBreakWinner = tieBreakEngine.calculation(concretePlayerScore.getPlayer(), tennisMatch);
           /// win a set via tie-break or not;
           if (tieBreakWinner.isPresent()){
               concretePlayerScore.addSetsScore();
               resetGamesForBothPlayers(concretePlayerScore, rivalPlayerScore);
               return Optional.of(concretePlayerScore.getPlayer());
           }
            return Optional.empty();
        }

        concretePlayerScore.addGamesScore();
        /// Is tie-break need to be activated
        if  (concretePlayerScore.getGames() == 6 && rivalPlayerScore.getGames() == 6){ /// tie-break activation
            tennisMatch.activateTieBreak();
            return Optional.empty();
        }

        /// regular set flow
        if (concretePlayerScore.getGames() == 6 && rivalPlayerScore.getGames() < 5){
            concretePlayerScore.addSetsScore();
            resetGamesForBothPlayers(concretePlayerScore, rivalPlayerScore);
            return Optional.of(concretePlayerScore.getPlayer());
        }
        return Optional.empty();
    }


    private void resetGamesForBothPlayers(PlayerScore concretePlayerScore, PlayerScore rivalPlayerScore) {
        concretePlayerScore.resetGames();
        rivalPlayerScore.resetGames();
    }



}
