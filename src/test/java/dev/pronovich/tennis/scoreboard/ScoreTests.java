package dev.pronovich.tennis.scoreboard;

import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
import dev.pronovich.tennis.scoreboard.domain.Points;
import dev.pronovich.tennis.scoreboard.domain.engene.ScoreEngine;
import dev.pronovich.tennis.scoreboard.model.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ScoreTests {

    @ParameterizedTest
    @MethodSource("provideWinningScorePairs")
    @DisplayName("Base win of the game: 40 - 30")
    void concretePlayerShouldWinGame_WithVariousScoreCombinatioons(Points concretePlayerPoints, Points rivalPlayerPoints){

        ScoreEngine scoreEngine = new ScoreEngine();
        Player concretePlayer = new Player("Bob");
        Player rivalPlayer = new Player("Jon");
        MatchScore matchScore = new MatchScore(
                new PlayerScore(concretePlayer),
                new PlayerScore(rivalPlayer));

        matchScore.getSpecificPlayerScore(concretePlayer).setPoints(concretePlayerPoints);
        matchScore.getSpecificPlayerScore(rivalPlayer).setPoints(rivalPlayerPoints);

        scoreEngine.calculation(concretePlayer, matchScore);

        assertEquals(Points.ZERO, matchScore.getSpecificPlayerScore(concretePlayer).getPoints());
        assertEquals(Points.ZERO, matchScore.getSpecificPlayerScore(rivalPlayer).getPoints());
        assertEquals(true, scoreEngine.isGameWon());
    }


    private static Stream<Arguments> provideWinningScorePairs(){
        return Stream.of(
                Arguments.of(Points.FORTY, Points.THIRTY),
                Arguments.of(Points.FORTY, Points.FIFTEEN),
                Arguments.of(Points.FORTY, Points.ZERO)
        );
    }
}
