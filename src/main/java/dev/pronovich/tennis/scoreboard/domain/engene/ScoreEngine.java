package dev.pronovich.tennis.scoreboard.domain.engene;

import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
import dev.pronovich.tennis.scoreboard.domain.Points;
import dev.pronovich.tennis.scoreboard.model.Player;

public class ScoreEngine implements Engine {

    private boolean gameIsWonByCooncretePlayer;

    @Override
    public void calculation(Player player, MatchScore matchScore) {

        gameIsWonByCooncretePlayer = false;
       PlayerScore concretePlayerScore = matchScore.getSpecificPlayerScore(player);
       PlayerScore rivalPlayerScore = matchScore.getAnotherPlayerScore(player);

        /// 40 / 30,15,0 -> winGame
        if (concretePlayerScore.getPoints() == Points.FORTY &&
                (rivalPlayerScore.getPoints().compareTo(Points.FORTY) < 0)){
            gameIsWonByCooncretePlayer = true;
            resetPointsOfBothPlayers(concretePlayerScore, rivalPlayerScore);
            return;
        }
        /// AD / 40  -> winGame
        if (concretePlayerScore.getPoints() == Points.ADVANTAGE &&
        rivalPlayerScore.getPoints() == Points.FORTY){
            concretePlayerScore.addPoints();
            gameIsWonByCooncretePlayer = true;
            resetPointsOfBothPlayers(concretePlayerScore, rivalPlayerScore);
            return;
        }

        /// 40 / AD -> 40 / 40
        if (concretePlayerScore.getPoints() == Points.FORTY &&
        rivalPlayerScore.getPoints() == Points.ADVANTAGE){
            concretePlayerScore.addPoints();
            gameIsWonByCooncretePlayer = true;
            resetPointsOfBothPlayers(concretePlayerScore, rivalPlayerScore);
            return;
        }

        concretePlayerScore.addPoints();

    }

    public boolean isGameWon() {
        return gameIsWonByCooncretePlayer;
    }

    private void resetPointsOfBothPlayers(PlayerScore concretePlayerScore, PlayerScore rivalPlayerScore) {
        concretePlayerScore.resetPoints();
        rivalPlayerScore.resetPoints();
    }
}
