package dev.pronovich.tennis.scoreboard.domain.engine;

import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
import dev.pronovich.tennis.scoreboard.model.Player;

import java.util.Optional;


public class SetEngine implements Engine {



    @Override
    public Optional<Player> calculation(Player player, MatchScore matchScore) {

        PlayerScore concretePlayerScore = matchScore.getSpecificPlayerScore(player);
        PlayerScore rivalPlayerScore = matchScore.getAnotherPlayerScore(player);

        /// regular set flow
        /*
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
         */



        /// TIE-BREAK
        /// increase tieBreakPoints for concretePlayer by 1
        concretePlayerScore.addTieBreakPoints();

        /// if Concrete player has 7 points with range of 2 or more he wins
        if (concretePlayerScore.getTieBreakPoints() >= 7 && isTieBreakPointsDifferenceMoreThenTwo(concretePlayerScore, rivalPlayerScore)){
            matchScore.deactivateTieBreak();
            matchScore.setTieBreakWon(true);
        }
        return Optional.empty();
    }




    private boolean isTieBreakPointsDifferenceMoreThenTwo(PlayerScore concretePlayerScore, PlayerScore rivalPlayerScore) {
        return concretePlayerScore.getTieBreakPoints() - rivalPlayerScore.getTieBreakPoints() >= 2;
    }

}
