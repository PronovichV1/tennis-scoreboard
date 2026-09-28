package dev.pronovich.tennis.scoreboard.domain.engine;

import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
import dev.pronovich.tennis.scoreboard.domain.Points;
import dev.pronovich.tennis.scoreboard.model.Player;
import lombok.Getter;

@Getter
public class ScoreEngine implements Engine {


    @Override
    public void calculation(Player player, MatchScore matchScore) {
        matchScore.setGameWon(false);

       PlayerScore concretePlayerScore = matchScore.getSpecificPlayerScore(player);
       PlayerScore rivalPlayerScore = matchScore.getAnotherPlayerScore(player);

        /// 40 / 30,15,0 -> winGame
        if (concretePlayerScore.getPoints() == Points.FORTY &&
                (rivalPlayerScore.getPoints().compareTo(Points.FORTY) < 0)){
            matchScore.setGameWon(true);
            resetPointsOfBothPlayers(concretePlayerScore, rivalPlayerScore);
            return;
        }
        /// AD / 40  -> winGame
        if (concretePlayerScore.getPoints() == Points.ADVANTAGE &&
        rivalPlayerScore.getPoints() == Points.FORTY){
            matchScore.setGameWon(true);
            resetPointsOfBothPlayers(concretePlayerScore, rivalPlayerScore);
            return;
        }

        /// 40 / AD -> 40 / 40
        if (concretePlayerScore.getPoints() == Points.FORTY &&
        rivalPlayerScore.getPoints() == Points.ADVANTAGE){
            rivalPlayerScore.setPoints(Points.FORTY);
            return;
        }
        concretePlayerScore.addPoints();
    }

    private void resetPointsOfBothPlayers(PlayerScore concretePlayerScore, PlayerScore rivalPlayerScore) {
        concretePlayerScore.resetPoints();
        rivalPlayerScore.resetPoints();
    }
}
