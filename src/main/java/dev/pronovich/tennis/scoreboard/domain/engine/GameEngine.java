package dev.pronovich.tennis.scoreboard.domain.engine;

import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
import dev.pronovich.tennis.scoreboard.domain.Points;
import dev.pronovich.tennis.scoreboard.domain.model.TennisPlayer;
import lombok.Getter;

import java.util.Optional;

@Getter
public class GameEngine implements Engine {


    @Override
    public Optional<TennisPlayer> calculation(TennisPlayer player, MatchScore matchScore) {

        PlayerScore concretePlayerScore = matchScore.getSpecificPlayerScore(player);
        PlayerScore rivalPlayerScore = matchScore.getAnotherPlayerScore(player);

        /// 40 / 30,15,0 -> winGame
        if (concretePlayerScore.getPoints() == Points.FORTY &&
                (rivalPlayerScore.getPoints().compareTo(Points.FORTY) < 0)) {
            resetPointsOfBothPlayers(concretePlayerScore, rivalPlayerScore);
            return Optional.of(concretePlayerScore.getPlayer());
        }
        /// AD / 40  -> winGame
        if (concretePlayerScore.getPoints() == Points.ADVANTAGE &&
                rivalPlayerScore.getPoints() == Points.FORTY) {
            resetPointsOfBothPlayers(concretePlayerScore, rivalPlayerScore);
            return Optional.of(concretePlayerScore.getPlayer());
        }
        /// 40 / AD -> 40 / 40
        if (concretePlayerScore.getPoints() == Points.FORTY &&
                rivalPlayerScore.getPoints() == Points.ADVANTAGE) {
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
