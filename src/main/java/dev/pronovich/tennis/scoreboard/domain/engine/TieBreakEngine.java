package dev.pronovich.tennis.scoreboard.domain.engine;

import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
import dev.pronovich.tennis.scoreboard.model.Player;


public class TieBreakEngine implements Engine {




    @Override
    public void calculation(Player player, MatchScore matchScore) {
        PlayerScore concretePlayerScore = matchScore.getSpecificPlayerScore(player);
        PlayerScore rivalPlayerScore = matchScore.getAnotherPlayerScore(player);

        /// increase tieBreakPoints for concretePlayer by 1
        concretePlayerScore.addTieBreakPoints();

        /// if Concrete player has 7 points with range of 2 or more he wins
        if (concretePlayerScore.getTieBreakPoints() >= 7 && isTieBreakPointsDifferenceMoreThenTwo(concretePlayerScore, rivalPlayerScore)){
            matchScore.deactivateTieBreak();
            matchScore.setTieBreakWon(true);
        }
    }




    private boolean isTieBreakPointsDifferenceMoreThenTwo(PlayerScore concretePlayerScore, PlayerScore rivalPlayerScore) {
        return concretePlayerScore.getTieBreakPoints() - rivalPlayerScore.getTieBreakPoints() >= 2;
    }

}
