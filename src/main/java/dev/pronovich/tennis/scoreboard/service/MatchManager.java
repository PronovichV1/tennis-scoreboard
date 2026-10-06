package dev.pronovich.tennis.scoreboard.service;


import dev.pronovich.tennis.scoreboard.dao.PlayerDaoImpl;
import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
import dev.pronovich.tennis.scoreboard.domain.model.TennisPlayer;
import dev.pronovich.tennis.scoreboard.domain.engine.MatchEngine;
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
    private PlayerMapper playerMapper; /// remove later
    private MatchMapper matchMapper; /// remove later

    @Autowired
    public MatchManager(OngoingMatches ongoingMatches, FinishedMatchManager finishedMatchManager, PlayerDaoImpl playerDao
            , PlayerMapper playerMapper,  MatchMapper matchMapper) {
        this.ongoingMatches = ongoingMatches;
        this.matchEngine = new MatchEngine();
        this.finishedMatchManager = finishedMatchManager;
        this.playerDao = playerDao;
        this.playerMapper = playerMapper;
        this.matchMapper = matchMapper;
    }


    public MatchScore generateMatch(String player1Name, String player2Name) {
        Player firstPlayer = playerDao.findByName(player1Name).orElseGet(() -> new Player(player1Name));
        Player secondPayer = playerDao.findByName(player2Name).orElseGet(() -> new Player(player2Name));
        TennisPlayer player1Domain = new TennisPlayer(firstPlayer.getUuid(), firstPlayer.getName());
        TennisPlayer player2Domain = new TennisPlayer(secondPayer.getUuid(), secondPayer.getName());
        PlayerScore playerScore1 = new PlayerScore(player1Domain);
        PlayerScore playerScore2 = new PlayerScore(player2Domain);
        MatchScore matchScore = new MatchScore(playerScore1, playerScore2);
        ongoingMatches.save(matchScore);
        return matchScore;
    }



    public Match addPointsTo(MatchScore matchScore, TennisPlayer tennisPlayer) {
        MatchScore matchScore = ongoingMatches.findById(matchScore.getUuid());
        Optional<TennisPlayer> matchWinner = matchEngine.calculation(tennisPlayer, matchScore);

        if (matchWinner.isPresent()) {
            currentMatch.setWinner(currentMatch.getTennisMatch().getWinner());
            Match matchEntity = matchMapper.toMatchEntity(matchScore);
            Player player = playerMapper.toPlayerEntity(matchWinner);
            matchEntity.setWinner(player);
            finishedMatchManager.save(matchEntity);
            ongoingMatches.remove(matchScore.getUuid());
        }
        return currentMatch;
    }
}
