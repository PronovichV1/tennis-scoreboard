package dev.pronovich.tennis.scoreboard.domain.engine;

import dev.pronovich.tennis.scoreboard.domain.TennisMatch;
import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
import dev.pronovich.tennis.scoreboard.domain.Points;
import dev.pronovich.tennis.scoreboard.model.Player;
import lombok.Getter;

import java.util.Optional;

@Getter
public class GameEngine implements Engine {


    @Override
    public Optional<Player> calculation(Player player, TennisMatch tennisMatch) {

       PlayerScore concretePlayerScore = tennisMatch.getSpecificPlayerScore(player);
       PlayerScore rivalPlayerScore = tennisMatch.getAnotherPlayerScore(player);

        /// 40 / 30,15,0 -> winGame
        if (concretePlayerScore.getPoints() == Points.FORTY &&
                (rivalPlayerScore.getPoints().compareTo(Points.FORTY) < 0)){
            resetPointsOfBothPlayers(concretePlayerScore, rivalPlayerScore);
            return Optional.of(concretePlayerScore.getPlayer());
        }
        /// AD / 40  -> winGame
        if (concretePlayerScore.getPoints() == Points.ADVANTAGE &&
        rivalPlayerScore.getPoints() == Points.FORTY){
            resetPointsOfBothPlayers(concretePlayerScore, rivalPlayerScore);
            return Optional.of(concretePlayerScore.getPlayer());
        }
        /// 40 / AD -> 40 / 40
        if (concretePlayerScore.getPoints() == Points.FORTY &&
        rivalPlayerScore.getPoints() == Points.ADVANTAGE){
            rivalPlayerScore.setPoints(Points.FORTY);
            return Optional.empty();
        }

        concretePlayerScore.addPoints();
        return Optional.empty();
    }

    private void resetPointsOfBothPlayers(PlayerScore concretePlayerScore, PlayerScore rivalPlayerScore) {
        concretePlayerScore.resetPoints();
        rivalPlayerScore.resetPoints();
    }
}
