package dev.pronovich.tennis.scoreboard;

import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
import dev.pronovich.tennis.scoreboard.domain.Points;
import dev.pronovich.tennis.scoreboard.domain.engine.MatchEngine;
import dev.pronovich.tennis.scoreboard.domain.engine.SetEngine;
import dev.pronovich.tennis.scoreboard.domain.model.TennisPlayer;
import dev.pronovich.tennis.scoreboard.model.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class MatchTests {


    private static TennisPlayer concretePlayer;
    private static TennisPlayer rivalPlayer;


    private PlayerScore concretePlayerMatchScore;
    private PlayerScore rivalPlayerMatchScore;
    private MatchScore matchScore;
    private MatchEngine matchEngine;
    private SetEngine setEngine;



    @BeforeAll
    static void setUpBeforeAll(){
        concretePlayer = new TennisPlayer(UUID.randomUUID(), "Bob");
        rivalPlayer = new TennisPlayer(UUID.randomUUID(), "Jon");

    }

    @BeforeEach
    void setUpBeforeEach(){
        setEngine = new SetEngine();
        matchEngine = new MatchEngine();
        concretePlayerMatchScore = new PlayerScore(concretePlayer);
        rivalPlayerMatchScore = new PlayerScore(rivalPlayer);
        matchScore = new MatchScore(concretePlayerMatchScore, rivalPlayerMatchScore);
    }




    @Test
    @DisplayName("Concrete player win a match: 2:0")
    void concretePlayer_winMatch(){

        matchScore.getSpecificPlayerScore(concretePlayer).setPoints(Points.FORTY);
        matchScore.getSpecificPlayerScore(rivalPlayer).setPoints(Points.ZERO);

        matchScore.getSpecificPlayerScore(concretePlayer).setGames(5);
        matchScore.getSpecificPlayerScore(rivalPlayer).setGames(0);

        matchScore.getSpecificPlayerScore(concretePlayer).setSets(1);
        matchScore.getSpecificPlayerScore(rivalPlayer).setSets(0);

        Optional<TennisPlayer> winner = matchEngine.calculation(concretePlayer, matchScore);

        assertEquals(matchScore.getWinner().name(), concretePlayer.name());
        assertEquals(true, winner.isPresent());
    }

}
