package dev.pronovich.tennis.scoreboard.domain.engine;

import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
import dev.pronovich.tennis.scoreboard.model.Player;
import org.springframework.stereotype.Service;


public class TieBreakEngine implements Engine {



    private boolean isTieBreak;
    private boolean isTieBreakIsWonByConcretePlayer;


    @Override
    public void calculation(Player player, MatchScore matchScore) {
        isTieBreakIsWonByConcretePlayer = false;
        PlayerScore concretePlayerScore = matchScore.getSpecificPlayerScore(player);
        PlayerScore rivalPlayerScore = matchScore.getAnotherPlayerScore(player);

        /// increase tieBreakPoints for concretePlayer by 1
        concretePlayerScore.addTieBreakPoints();

        /// if Concrete player has 7 points with range of 2 or more he wins
        if (concretePlayerScore.getTieBreakPoints() >= 7 && isTieBreakPointsDifferenceMoreThenTwo(concretePlayerScore, rivalPlayerScore)){
            deactivateTieBreak(matchScore);
            isTieBreakIsWonByConcretePlayer = true;
        }
    }

    protected void activateTieBreak(MatchScore matchScore){
        isTieBreak = true;
        matchScore.getPlayerScore1().setTieBreakPoints(0);
        matchScore.getPlayerScore2().setTieBreakPoints(0);
    }

    private void deactivateTieBreak(MatchScore matchScore) {
        isTieBreak = false;
        matchScore.getPlayerScore1().setTieBreakPoints(null);
        matchScore.getPlayerScore2().setTieBreakPoints(null);
    }

    private boolean isTieBreakPointsDifferenceMoreThenTwo(PlayerScore concretePlayerScore, PlayerScore rivalPlayerScore) {
        return concretePlayerScore.getTieBreakPoints() - rivalPlayerScore.getTieBreakPoints() >= 2;
    }


    public boolean isOn() {
        return isTieBreak;
    }

    public boolean isTieBreakWon() {
        return isTieBreakIsWonByConcretePlayer;
    }

}
