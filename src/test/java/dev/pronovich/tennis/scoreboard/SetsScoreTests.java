package dev.pronovich.tennis.scoreboard;

import dev.pronovich.tennis.scoreboard.domain.TennisMatch;
import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
import dev.pronovich.tennis.scoreboard.domain.Points;
import dev.pronovich.tennis.scoreboard.domain.engine.MatchEngine;
import dev.pronovich.tennis.scoreboard.domain.engine.SetEngine;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class SetsScoreTests {

    private static Player concretePlayer;
    private static Player rivalPlayer;


    private PlayerScore concretePlayerMatchScore;
    private PlayerScore rivalPlayerMatchScore;
    private TennisMatch tennisMatch;
    private MatchEngine matchEngine;
    private SetEngine setEngine;



    @BeforeAll
    static void setUpBeforeAll(){
        concretePlayer = new Player("Bob");
        rivalPlayer = new Player("Jon");

    }

    @BeforeEach
    void setUpBeforeEach(){
        setEngine = new SetEngine();
        matchEngine = new MatchEngine();
        concretePlayerMatchScore = new PlayerScore(concretePlayer);
        rivalPlayerMatchScore = new PlayerScore(rivalPlayer);
        tennisMatch = new TennisMatch(concretePlayerMatchScore, rivalPlayerMatchScore);
    }


    @ParameterizedTest
    @MethodSource("provideWinGameScores")
    @DisplayName("Concrete player win a set: 6 - 4,3,2,1,0")
    void playerWinGame_WhenPlayerHasSixGamesAndTwoGamesLead(Integer concretePlayerGameScore, Integer rivalPlayerGameScore){

        tennisMatch.getSpecificPlayerScore(concretePlayer).setPoints(Points.FORTY);
        tennisMatch.getSpecificPlayerScore(rivalPlayer).setPoints(Points.ZERO);
        tennisMatch.getSpecificPlayerScore(concretePlayer).setGames(concretePlayerGameScore);
        tennisMatch.getSpecificPlayerScore(rivalPlayer).setGames(rivalPlayerGameScore);

        Optional<Player> setWinner = setEngine.calculation(concretePlayer, tennisMatch);

        assertEquals(1, tennisMatch.getSpecificPlayerScore(concretePlayer).getSets());
        assertEquals(0, tennisMatch.getSpecificPlayerScore(concretePlayer).getGames());
        assertEquals(0, tennisMatch.getSpecificPlayerScore(rivalPlayer).getSets());
        assertEquals(0, tennisMatch.getSpecificPlayerScore(rivalPlayer).getGames());
        assertEquals(true, setWinner.isPresent());
    }

    /// win a set after tie-break games 6-6 -> isTieBreakWon. Set 1-0
    @Test
    @DisplayName("Concrete player win a set after win in a tie-break")
    void concretePlayer_winTieBreak_winSet(){

        tennisMatch.getSpecificPlayerScore(concretePlayer).setGames(5);
        tennisMatch.getSpecificPlayerScore(rivalPlayer).setGames(6);
        tennisMatch.getSpecificPlayerScore(concretePlayer).setPoints(Points.FORTY);
        tennisMatch.getSpecificPlayerScore(rivalPlayer).setPoints(Points.ZERO);

        matchEngine.calculation(concretePlayer, tennisMatch);
        tennisMatch.getSpecificPlayerScore(concretePlayer).setTieBreakPoints(7);
        tennisMatch.getSpecificPlayerScore(rivalPlayer).setTieBreakPoints(5);


        Optional<Player> setWinner = setEngine.calculation(concretePlayer, tennisMatch);

        assertEquals(1, tennisMatch.getSpecificPlayerScore(concretePlayer).getSets());
        assertEquals(0, tennisMatch.getSpecificPlayerScore(rivalPlayer).getSets());
        assertEquals(0, tennisMatch.getSpecificPlayerScore(concretePlayer).getGames());
        assertEquals(0, tennisMatch.getSpecificPlayerScore(rivalPlayer).getGames());
        assertEquals(true, setWinner.isPresent());
    }

    @Test
    @DisplayName("Both players have 6 games")
    void playersHasTie_WhenBothPlayersHasSixGames(){

        tennisMatch.getSpecificPlayerScore(concretePlayer).setPoints(Points.FORTY);
        matchEngine.calculation(concretePlayer, tennisMatch);


        tennisMatch.getSpecificPlayerScore(concretePlayer).setPoints(Points.FORTY);
        tennisMatch.getSpecificPlayerScore(rivalPlayer).setPoints(Points.ZERO);
        tennisMatch.getSpecificPlayerScore(concretePlayer).setGames(5);
        tennisMatch.getSpecificPlayerScore(rivalPlayer).setGames(6);

        Optional<Player> setWinner = setEngine.calculation(concretePlayer, tennisMatch);

        assertEquals(0, tennisMatch.getSpecificPlayerScore(concretePlayer).getSets());
        assertEquals(6, tennisMatch.getSpecificPlayerScore(concretePlayer).getGames());
        assertEquals(0, tennisMatch.getSpecificPlayerScore(rivalPlayer).getSets());
        assertEquals(6, tennisMatch.getSpecificPlayerScore(rivalPlayer).getGames());

        assertEquals(false, setWinner.isPresent());

    }


    private static Stream<Arguments> provideWinGameScores(){
        return Stream.of(Arguments.of(5, 4),
                Arguments.of(5, 3),
                Arguments.of(5, 2),
                Arguments.of(5, 1),
                Arguments.of(5, 0)
        );
    }

}
