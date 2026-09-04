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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ScoreTests {


    /// ADD BEFORE EACH
    /// ADD BEFORE ALL


    @Test
    @DisplayName("Win with advantage: AD - 40")
    void playerWithAdvantageShouldWinTheGame(){
        /// Arrange
        ScoreEngine scoreEngine = new ScoreEngine();
        Player concretePlayer = new Player("Bob");
        Player rivalPlayer = new Player("Jon");
        MatchScore matchScore = new MatchScore(
                new PlayerScore(concretePlayer),
                new PlayerScore(rivalPlayer));

        matchScore.getSpecificPlayerScore(concretePlayer).setPoints(Points.ADVANTAGE);
        matchScore.getSpecificPlayerScore(rivalPlayer).setPoints(Points.FORTY);
        /// Act
        scoreEngine.calculation(concretePlayer, matchScore);
        ///Assert
        assertEquals(Points.ZERO, matchScore.getSpecificPlayerScore(concretePlayer).getPoints());
        assertEquals(Points.ZERO, matchScore.getSpecificPlayerScore(rivalPlayer).getPoints());
        assertEquals(true, scoreEngine.isGameIsWonByConcretePlayer());
    }

    @Test
    @DisplayName("Return to DEUCE: 40 - AD")
    void playerWithAdvantageLosesPointAndScoreReturnsToDeuce(){
        /// Arrange
        ScoreEngine scoreEngine = new ScoreEngine();
        Player concretePlayer = new Player("Bob");
        Player rivalPlayer = new Player("Jon");
        MatchScore matchScore = new MatchScore(
                new PlayerScore(concretePlayer),
                new PlayerScore(rivalPlayer));

        matchScore.getSpecificPlayerScore(concretePlayer).setPoints(Points.FORTY);
        matchScore.getSpecificPlayerScore(rivalPlayer).setPoints(Points.ADVANTAGE);
        /// Act
        scoreEngine.calculation(concretePlayer, matchScore);
        ///Assert
        assertEquals(Points.FORTY, matchScore.getSpecificPlayerScore(concretePlayer).getPoints());
        assertEquals(Points.FORTY, matchScore.getSpecificPlayerScore(rivalPlayer).getPoints());
    }


    @ParameterizedTest
    @MethodSource("provideWinningScorePairs")
    @DisplayName("Base win of the game: 40 - 30")
    void concretePlayerShouldWinGame_WithVariousScoreCombinations(Points concretePlayerPoints, Points rivalPlayerPoints){

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
        assertEquals(true, scoreEngine.isGameIsWonByConcretePlayer());
    }


    private static Stream<Arguments> provideWinningScorePairs(){
        return Stream.of(
                Arguments.of(Points.FORTY, Points.THIRTY),
                Arguments.of(Points.FORTY, Points.FIFTEEN),
                Arguments.of(Points.FORTY, Points.ZERO)
        );
    }



}
