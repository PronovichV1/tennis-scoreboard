package dev.pronovich.tennis.scoreboard;

import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
import dev.pronovich.tennis.scoreboard.domain.Points;
import dev.pronovich.tennis.scoreboard.domain.engene.MatchEngine;
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

import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class MatchTests {


    private static Player concretePlayer;
    private static Player rivalPlayer;
    private static MatchEngine matchEngine;
    private static MatchScore matchScore;

    private PlayerScore concretePlayerMatchScore;
    private PlayerScore rivalPlayerMatchScore;



    @BeforeAll
    static void setUpBeforeAll(){
        concretePlayer = new Player("Bob");
        rivalPlayer = new Player("Jon");
        matchScore = new MatchScore(
                new PlayerScore(concretePlayer),
                new PlayerScore(rivalPlayer));
        matchEngine = new MatchEngine();
    }

    @BeforeEach
    void setUpBeforeEach(){
        concretePlayerMatchScore = new PlayerScore(concretePlayer);
        rivalPlayerMatchScore = new PlayerScore(rivalPlayer);
        matchScore = new MatchScore(concretePlayerMatchScore, rivalPlayerMatchScore);
    }

    @Test
    @DisplayName("Both players have 6 games")
    void playersHasTie_WhenBothPlayersHasSixGames(){

        matchScore.getSpecificPlayerScore(concretePlayer).setPoints(Points.FORTY);
        matchEngine.calculation(concretePlayer, matchScore);


        matchScore.getSpecificPlayerScore(concretePlayer).setPoints(Points.FORTY);
        matchScore.getSpecificPlayerScore(rivalPlayer).setPoints(Points.ZERO);
        matchScore.getSpecificPlayerScore(concretePlayer).setGames(5);
        matchScore.getSpecificPlayerScore(rivalPlayer).setGames(6);

        matchEngine.calculation(concretePlayer, matchScore);


        assertEquals(0, matchScore.getSpecificPlayerScore(concretePlayer).getSets());
        assertEquals(6, matchScore.getSpecificPlayerScore(concretePlayer).getGames());
        assertEquals(0, matchScore.getSpecificPlayerScore(rivalPlayer).getSets());
        assertEquals(6, matchScore.getSpecificPlayerScore(rivalPlayer).getGames());
    }



    @ParameterizedTest
    @MethodSource("provideWinGameScores")
    @DisplayName("Concrete player win a set: 6 - 4,3,2,1,0")
    void playerWinGame_WhenPlayerHasSixSetsAndTwoSetLead(Integer concretePlayerGameScore, Integer rivalPlayerGameScore){


        matchScore.getSpecificPlayerScore(concretePlayer).setPoints(Points.FORTY);
        matchScore.getSpecificPlayerScore(rivalPlayer).setPoints(Points.ZERO);
        matchScore.getSpecificPlayerScore(concretePlayer).setGames(concretePlayerGameScore);
        matchScore.getSpecificPlayerScore(rivalPlayer).setGames(rivalPlayerGameScore);

       matchEngine.calculation(concretePlayer, matchScore);


       assertEquals(1, matchScore.getSpecificPlayerScore(concretePlayer).getSets());
       assertEquals(0, matchScore.getSpecificPlayerScore(concretePlayer).getGames());
       assertEquals(0, matchScore.getSpecificPlayerScore(rivalPlayer).getSets());
       assertEquals(0, matchScore.getSpecificPlayerScore(rivalPlayer).getGames());

    }


    private static Stream<Arguments> provideWinGameScores(){
        return Stream.of(Arguments.of(5, 4),
                Arguments.of(5, 3),
                Arguments.of(5, 2),
                Arguments.of(5, 1)
        );
    }



}
