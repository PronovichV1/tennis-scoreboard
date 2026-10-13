package dev.pronovich.tennis.scoreboard.service;


import dev.pronovich.tennis.scoreboard.dao.PlayerDaoImpl;
<<<<<<< HEAD
=======
import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
>>>>>>> refactoring
import dev.pronovich.tennis.scoreboard.domain.engine.MatchEngine;
import dev.pronovich.tennis.scoreboard.domain.model.TennisPlayer;
import dev.pronovich.tennis.scoreboard.mapper.MatchMapper;
import dev.pronovich.tennis.scoreboard.mapper.PlayerMapper;
import dev.pronovich.tennis.scoreboard.model.Match;
import dev.pronovich.tennis.scoreboard.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatchManager {


    private OngoingMatches ongoingMatches;
    private MatchEngine matchEngine;
    private FinishedMatchManager finishedMatchManager;
    private PlayerDaoImpl playerDao;
<<<<<<< HEAD

    @Autowired
    public MatchManager(OngoingMatches ongoingMatches, FinishedMatchManager finishedMatchManager, PlayerDaoImpl playerDao) {
=======
    private PlayerMapper playerMapper; /// remove later
    private MatchMapper matchMapper;
    @Autowired
    public MatchManager(OngoingMatches ongoingMatches, FinishedMatchManager finishedMatchManager, PlayerDaoImpl playerDao
            , PlayerMapper playerMapper,  MatchMapper matchMapper) {
>>>>>>> refactoring
        this.ongoingMatches = ongoingMatches;
        this.matchEngine = new MatchEngine();
        this.finishedMatchManager = finishedMatchManager;
        this.playerDao = playerDao;
<<<<<<< HEAD
    }


    public Match generateMatch(String player1Name, String player2Name) {
        Player player1 = playerDao.findByName(player1Name).orElseGet(() -> new Player(player1Name));
        Player player2 = playerDao.findByName(player2Name).orElseGet(() -> new Player(player2Name));
        Match match = new Match(player1, player2);
        ongoingMatches.save(match);
        return match;
=======
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
>>>>>>> refactoring
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
