package dev.pronovich.tennis.scoreboard;

import dev.pronovich.tennis.scoreboard.domain.TennisMatch;
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
    private static TennisMatch tennisMatch;
    private static Player concretePlayer;
    private static Player rivalPlayer;

    private PlayerScore concretePlayerMatchScore;
    private PlayerScore rivalPlayerMatchScore;


    @BeforeAll
    static void setUpBase(){
        gameEngine = new GameEngine();
        concretePlayer = new Player("Bob");
        rivalPlayer = new Player("Jon");
        tennisMatch = new TennisMatch(
                new PlayerScore(concretePlayer),
                new PlayerScore(rivalPlayer));
    }


    @BeforeEach
    void preSetUpMatchScore(){
        concretePlayerMatchScore = new PlayerScore(concretePlayer);
        rivalPlayerMatchScore = new PlayerScore(rivalPlayer);
        tennisMatch = new TennisMatch(concretePlayerMatchScore, rivalPlayerMatchScore);
    }


    @Test
    @DisplayName("Win with advantage: AD - 40")
    void playerWithAdvantageShouldWinTheGame(){

        tennisMatch.getSpecificPlayerScore(concretePlayer).setPoints(Points.ADVANTAGE);
        tennisMatch.getSpecificPlayerScore(rivalPlayer).setPoints(Points.FORTY);
        /// Act
        Optional<Player> gameWinner = gameEngine.calculation(concretePlayer, tennisMatch);
        ///Assert
        assertEquals(Points.ZERO, tennisMatch.getSpecificPlayerScore(concretePlayer).getPoints());
        assertEquals(Points.ZERO, tennisMatch.getSpecificPlayerScore(rivalPlayer).getPoints());
        assertEquals(true, gameWinner.isPresent());
    }

    @Test
    @DisplayName("Return to DEUCE: 40 - AD")
    void playerWithAdvantageLosesPointAndScoreReturnsToDeuce(){

        tennisMatch.getSpecificPlayerScore(concretePlayer).setPoints(Points.FORTY);
        tennisMatch.getSpecificPlayerScore(rivalPlayer).setPoints(Points.ADVANTAGE);
        /// Act
        Optional<Player> gameWinner = gameEngine.calculation(concretePlayer, tennisMatch);
        ///Assert
        assertEquals(Points.FORTY, tennisMatch.getSpecificPlayerScore(concretePlayer).getPoints());
        assertEquals(Points.FORTY, tennisMatch.getSpecificPlayerScore(rivalPlayer).getPoints());
        assertEquals(true, gameWinner.isEmpty());
    }


    @ParameterizedTest
    @MethodSource("provideWinningScorePairs")
    @DisplayName("Base win of the game: 40 - 30")
    void concretePlayerShouldWinGame_WithVariousScoreCombinations(Points concretePlayerPoints, Points rivalPlayerPoints){

        tennisMatch.getSpecificPlayerScore(concretePlayer).setPoints(concretePlayerPoints);
        tennisMatch.getSpecificPlayerScore(rivalPlayer).setPoints(rivalPlayerPoints);

        Optional<Player> gameWinner = gameEngine.calculation(concretePlayer, tennisMatch);

        assertEquals(Points.ZERO, tennisMatch.getSpecificPlayerScore(concretePlayer).getPoints());
        assertEquals(Points.ZERO, tennisMatch.getSpecificPlayerScore(rivalPlayer).getPoints());
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
