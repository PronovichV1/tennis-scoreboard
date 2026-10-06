package dev.pronovich.tennis.scoreboard;

import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
import dev.pronovich.tennis.scoreboard.domain.Points;
import dev.pronovich.tennis.scoreboard.domain.engine.GameEngine;
import dev.pronovich.tennis.scoreboard.model.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GameScoreTests {


    private static GameEngine gameEngine;
    private static MatchScore matchScore;
    private static Player concretePlayer;
    private static Player rivalPlayer;

    private PlayerScore concretePlayerMatchScore;
    private PlayerScore rivalPlayerMatchScore;


    @BeforeAll
    static void setUpBase(){
        gameEngine = new GameEngine();
        concretePlayer = new Player("Bob");
        rivalPlayer = new Player("Jon");
        matchScore = new MatchScore(
                new PlayerScore(concretePlayer),
                new PlayerScore(rivalPlayer));
    }


    @BeforeEach
    void preSetUpMatchScore(){
        concretePlayerMatchScore = new PlayerScore(concretePlayer);
        rivalPlayerMatchScore = new PlayerScore(rivalPlayer);
        matchScore = new MatchScore(concretePlayerMatchScore, rivalPlayerMatchScore);
    }


    @Test
    @DisplayName("Win with advantage: AD - 40")
    void playerWithAdvantageShouldWinTheGame(){

        matchScore.getSpecificPlayerScore(concretePlayer).setPoints(Points.ADVANTAGE);
        matchScore.getSpecificPlayerScore(rivalPlayer).setPoints(Points.FORTY);
        /// Act
        Optional<Player> gameWinner = gameEngine.calculation(concretePlayer, matchScore);
        ///Assert
        assertEquals(Points.ZERO, matchScore.getSpecificPlayerScore(concretePlayer).getPoints());
        assertEquals(Points.ZERO, matchScore.getSpecificPlayerScore(rivalPlayer).getPoints());
        assertEquals(true, gameWinner.isPresent());
    }

    @Test
    @DisplayName("Return to DEUCE: 40 - AD")
    void playerWithAdvantageLosesPointAndScoreReturnsToDeuce(){

        matchScore.getSpecificPlayerScore(concretePlayer).setPoints(Points.FORTY);
        matchScore.getSpecificPlayerScore(rivalPlayer).setPoints(Points.ADVANTAGE);
        /// Act
        Optional<Player> gameWinner = gameEngine.calculation(concretePlayer, matchScore);
        ///Assert
        assertEquals(Points.FORTY, matchScore.getSpecificPlayerScore(concretePlayer).getPoints());
        assertEquals(Points.FORTY, matchScore.getSpecificPlayerScore(rivalPlayer).getPoints());
        assertEquals(true, gameWinner.isEmpty());
    }


    @ParameterizedTest
    @MethodSource("provideWinningScorePairs")
    @DisplayName("Base win of the game: 40 - 30")
    void concretePlayerShouldWinGame_WithVariousScoreCombinations(Points concretePlayerPoints, Points rivalPlayerPoints){

        matchScore.getSpecificPlayerScore(concretePlayer).setPoints(concretePlayerPoints);
        matchScore.getSpecificPlayerScore(rivalPlayer).setPoints(rivalPlayerPoints);

        Optional<Player> gameWinner = gameEngine.calculation(concretePlayer, matchScore);

        assertEquals(Points.ZERO, matchScore.getSpecificPlayerScore(concretePlayer).getPoints());
        assertEquals(Points.ZERO, matchScore.getSpecificPlayerScore(rivalPlayer).getPoints());
        assertEquals(true, gameWinner.isPresent());
    }


    private static Stream<Arguments> provideWinningScorePairs(){
        return Stream.of(
                Arguments.of(Points.FORTY, Points.THIRTY),
                Arguments.of(Points.FORTY, Points.FIFTEEN),
                Arguments.of(Points.FORTY, Points.ZERO)
        );
    }



}
