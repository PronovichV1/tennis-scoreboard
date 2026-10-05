package dev.pronovich.tennis.scoreboard.service;


import dev.pronovich.tennis.scoreboard.dao.PlayerDaoImpl;
import dev.pronovich.tennis.scoreboard.domain.engine.MatchEngine;
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
    private PlayerMapper playerMapper;

    @Autowired
    public MatchManager(OngoingMatches ongoingMatches, FinishedMatchManager finishedMatchManager, PlayerDaoImpl playerDao, PlayerMapper playerMapper) {
        this.ongoingMatches = ongoingMatches;
        this.matchEngine = new MatchEngine();
        this.finishedMatchManager = finishedMatchManager;
        this.playerDao = playerDao;
        this.playerMapper = playerMapper;
    }


    public Match generateMatch(String player1Name, String player2Name) {
        Player player1 = playerDao.findByName(player1Name).orElseGet(() -> new Player(player1Name));
        Player player2 = playerDao.findByName(player2Name).orElseGet(() -> new Player(player2Name));
        Match match = new Match(player1, player2);
        ongoingMatches.save(match);
        return match;
    }

    public Match addPointsTo(Match match, Player player) {
        Match currentMatch = ongoingMatches.findById(match.getUuid());
        Optional<Player> matchWinner = matchEngine.calculation(player, currentMatch.getTennisMatch());
        if (matchWinner.isPresent()) {
            currentMatch.setWinner(currentMatch.getTennisMatch().getWinner());
            finishedMatchManager.save(match);
            ongoingMatches.remove(match.getUuid());
        }
        return currentMatch;
    }
}
