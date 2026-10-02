package dev.pronovich.tennis.scoreboard.domain.engine;

import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
import dev.pronovich.tennis.scoreboard.model.Player;

import java.util.Optional;


public class SetEngine implements Engine {

    private final TieBreakEngine tieBreakEngine;
    public SetEngine() {
        tieBreakEngine = new TieBreakEngine();
    }

    @Override
    public Optional<Player> calculation(Player player, MatchScore matchScore) {

        PlayerScore concretePlayerScore = matchScore.getSpecificPlayerScore(player);
        PlayerScore rivalPlayerScore = matchScore.getAnotherPlayerScore(player);

        /// Is it TieBreak is active?
        if (matchScore.isTieBreak()){
           Optional<Player> tieBreakWinner = tieBreakEngine.calculation(player, matchScore);
           /// win set via tie-break or not;
           if (tieBreakWinner.isPresent()){
               concretePlayerScore.addSetsScore();
               resetGamesForBothPlayers(concretePlayerScore, rivalPlayerScore);
               return Optional.of(player);
           }
            return Optional.empty();
        }

        /// Is tie-break need to be activated
        if  (concretePlayerScore.getGames() == 6 && rivalPlayerScore.getGames() == 6){ /// tie-break activation
            matchScore.activateTieBreak();
            return Optional.empty();
        }

        /// regular set flow
        if (concretePlayerScore.getGames() == 5 && rivalPlayerScore.getGames() < 5){
            concretePlayerScore.addSetsScore();
            resetGamesForBothPlayers(concretePlayerScore, rivalPlayerScore);
            return Optional.of(player);
        }

        concretePlayerScore.addGamesScore();
        return Optional.empty();
    }


    private void resetGamesForBothPlayers(PlayerScore concretePlayerScore, PlayerScore rivalPlayerScore) {
        concretePlayerScore.resetGames();
        rivalPlayerScore.resetGames();
    }



}
