package dev.pronovich.tennis.scoreboard;

import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
import dev.pronovich.tennis.scoreboard.model.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UtilityTest {


    @Test
    @DisplayName("Get the first Player Entity")
    void getFirstPlayer(){

        Player firstPlayer = new Player("Sample John");
        Player secondPlayer = new Player("Sample Tom");
        MatchScore matchScore = new MatchScore(new PlayerScore(firstPlayer)
                , new PlayerScore(secondPlayer));

        PlayerScore actualPlayerScore = matchScore.getSpecificPlayerScore(firstPlayer);

        assertEquals(firstPlayer, actualPlayerScore.getPlayer());
        assertEquals(firstPlayer.getName(), actualPlayerScore.getPlayer().getName());

    }

    @Test
    @DisplayName("Get the second Player Entity")
    void getSecondPlayer(){

        Player firstPlayer = new Player("Sample John");
        Player secondPlayer = new Player("Sample Tom");
        MatchScore matchScore = new MatchScore(new PlayerScore(firstPlayer)
                , new PlayerScore(secondPlayer));

        PlayerScore actualPlayerScore = matchScore.getAnotherPlayerScore(firstPlayer);

        assertEquals(secondPlayer, actualPlayerScore.getPlayer());
        assertEquals(secondPlayer.getName(), actualPlayerScore.getPlayer().getName());
    }

}
