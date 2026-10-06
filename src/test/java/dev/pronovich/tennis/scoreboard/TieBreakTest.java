package dev.pronovich.tennis.scoreboard;

import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
import dev.pronovich.tennis.scoreboard.domain.engine.TieBreakEngine;
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
public class TieBreakTest {


    private static TieBreakEngine tieBreakEngine;
    private static MatchScore matchScore;
    private static Player concretePlayer;
    private static Player rivalPlayer;

    private PlayerScore concretePlayerMatchScore;
    private PlayerScore rivalPlayerMatchScore;


    @BeforeAll
    static void setUpBase(){
        concretePlayer = new Player("Bob");
        rivalPlayer = new Player("Jon");
        matchScore = new MatchScore(
                new PlayerScore(concretePlayer),
                new PlayerScore(rivalPlayer));
        tieBreakEngine = new TieBreakEngine();
    }


    @BeforeEach
    void preSetUpMatchScore(){
        concretePlayerMatchScore = new PlayerScore(concretePlayer);
        rivalPlayerMatchScore = new PlayerScore(rivalPlayer);
        matchScore = new MatchScore(concretePlayerMatchScore, rivalPlayerMatchScore);
    }


    ///  Concrete player tie-break points = 7 and rival player tie-break points = 0,1,2,3,4,5
    ///  Concrete player win a tie-break
    @ParameterizedTest
    @MethodSource("provideTieBreakPointsForConcretePlayerWinTieBreak")
    @DisplayName("Tie-break: 7-0,1,2,3,4,5 score -> Concrete player wins a point -> score becomes 7-0,1,2,3,4,5 - tie-break is over")
    void concretePlayerWinTieBreak(Integer concretePlayerTieBreakPoints, Integer rivalPlayerTieBreakPoints){

        concretePlayerMatchScore.setTieBreakPoints(concretePlayerTieBreakPoints);
        rivalPlayerMatchScore.setTieBreakPoints(rivalPlayerTieBreakPoints);

        Optional<Player> tieBreakWinner = tieBreakEngine.calculation(concretePlayer, matchScore);
        assertEquals(true, tieBreakWinner.isPresent());
    }

    /// concretePTBP = 8 - rivalPTBP = 8  concretePWinTB tie
    @Test
    @DisplayName("Tie-break: 7-8 score -> Player 1 wins a point -> Score becomes 8-8, tie-break continues")
    void bothPlayersHaveSimilarAmountTieBreakPoints(){
        concretePlayerMatchScore.setTieBreakPoints(7);
        rivalPlayerMatchScore.setTieBreakPoints(8);

        Optional<Player> tieBreakWinner = tieBreakEngine.calculation(concretePlayer, matchScore);
        assertEquals(8, matchScore.getSpecificPlayerScore(concretePlayer).getTieBreakPoints());
        assertEquals(8, matchScore.getSpecificPlayerScore(rivalPlayer).getTieBreakPoints());
        assertEquals(true, tieBreakWinner.isEmpty());
    }


    /// concretePTBP = 10 - rivalPTBP = 8  concretePWinTB winATie
    @Test
    @DisplayName("Tie-break: 9-8 score -> Player 1 wins a point -> Score becomes 10-8, tie-break is over")
    void tieBreak_playerLeadsByTwoAfterSevenPoints_winsTieBreak(){
        concretePlayerMatchScore.setTieBreakPoints(7);
        rivalPlayerMatchScore.setTieBreakPoints(8);

        Optional<Player> tieBreakWinner = tieBreakEngine.calculation(concretePlayer, matchScore);
        assertEquals(8, matchScore.getSpecificPlayerScore(concretePlayer).getTieBreakPoints());
        assertEquals(8, matchScore.getSpecificPlayerScore(rivalPlayer).getTieBreakPoints());
        assertEquals(true,  tieBreakWinner.isEmpty());
    }



    /// ConcretePTBP = 1,2,3,4,5,6 - rivalPTBP = 0
    @ParameterizedTest
    @MethodSource("provideWinTieBreakPoints")
    void playerScoresPoint_duringTieBreak(Integer concretePlayerTieBreakPoints, Integer rivalPlayerTieBreakPoints){

        concretePlayerMatchScore.setTieBreakPoints(concretePlayerTieBreakPoints);
        rivalPlayerMatchScore.setTieBreakPoints(rivalPlayerTieBreakPoints);

        Optional<Player> tieBreakWinner = tieBreakEngine.calculation(concretePlayer, matchScore);

        assertEquals(concretePlayerTieBreakPoints + 1, matchScore.getSpecificPlayerScore(concretePlayer).getTieBreakPoints());
        assertEquals(0, matchScore.getSpecificPlayerScore(rivalPlayer).getTieBreakPoints());
        assertEquals(true,  tieBreakWinner.isEmpty());


    }

    private static Stream<Arguments> provideWinTieBreakPoints(){
        return Stream.of(Arguments.of(0,0),
                Arguments.of(1,0),
                Arguments.of(2,0),
                Arguments.of(3,0),
                Arguments.of(4,0),
                Arguments.of(5,0));
    }

    private static Stream<Arguments> provideTieBreakPointsForConcretePlayerWinTieBreak(){
        return Stream.of(Arguments.of(7,0),
                        Arguments.of(7,1),
                        Arguments.of(7,2),
                        Arguments.of(7,3),
                        Arguments.of(7,4),
                        Arguments.of(7,5));
    }
}
