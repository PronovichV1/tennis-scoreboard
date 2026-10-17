package dev.pronovich.tennis.scoreboard.service;


import dev.pronovich.tennis.scoreboard.dao.PlayerDaoImpl;
import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
import dev.pronovich.tennis.scoreboard.domain.engine.MatchEngine;
import dev.pronovich.tennis.scoreboard.domain.model.TennisPlayer;
import dev.pronovich.tennis.scoreboard.mapper.MatchMapper;
import dev.pronovich.tennis.scoreboard.mapper.PlayerMapper;
import dev.pronovich.tennis.scoreboard.model.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatchManager {


    private final OngoingMatches ongoingMatches;
    private final MatchEngine matchEngine;
    private final FinishedMatchManager finishedMatchManager;
    private final PlayerDaoImpl playerDao;
    private final PlayerMapper playerMapper;
    /// remove later
    private final MatchMapper matchMapper;

    @Autowired
    public MatchManager(OngoingMatches ongoingMatches, FinishedMatchManager finishedMatchManager, PlayerDaoImpl playerDao
            , PlayerMapper playerMapper, MatchMapper matchMapper) {
        this.ongoingMatches = ongoingMatches;
        this.matchEngine = new MatchEngine();
        this.finishedMatchManager = finishedMatchManager;
        this.playerDao = playerDao;
        this.playerMapper = playerMapper;
        this.matchMapper = matchMapper;
    }


    public MatchScore generateMatch(String player1Name, String player2Name) { /// DDD + second attempt
        TennisPlayer tennisPlayer1 = new TennisPlayer(player1Name);
        TennisPlayer tennisPlayer2 = new TennisPlayer(player2Name);
        PlayerScore playerScore1 = new PlayerScore(tennisPlayer1);
        PlayerScore playerScore2 = new PlayerScore(tennisPlayer2);
        MatchScore matchScore = new MatchScore(playerScore1, playerScore2);
        ongoingMatches.save(matchScore);
        return matchScore;
    }


    public MatchScore addPointsTo(MatchScore matchScore, TennisPlayer tennisPlayer) { /// DDD+
        MatchScore currentMatch = matchScore;
        Optional<TennisPlayer> matchWinner = matchEngine.calculation(tennisPlayer, currentMatch);
        if (matchWinner.isPresent()) {
            Match finishedMatch = matchMapper.toMatchEntity(currentMatch);
            Match matchAfterSave = finishedMatchManager.save(finishedMatch);
            ongoingMatches.remove(matchScore.getUuid());
        }
        return currentMatch;
    }
}
