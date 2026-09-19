package dev.pronovich.tennis.scoreboard.service;

import dev.pronovich.tennis.scoreboard.dao.MatchDaoImpl;
import dev.pronovich.tennis.scoreboard.domain.engine.MatchEngine;
import dev.pronovich.tennis.scoreboard.exception.MatchNotExistException;
import dev.pronovich.tennis.scoreboard.model.Match;
import dev.pronovich.tennis.scoreboard.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchManager {


    private OngoingMatches ongoingMatches;
    private MatchEngine matchEngine;
    private FinishedMatchManager finishedMatchManager;

    @Autowired
    public MatchManager(MatchEngine matchEngine, FinishedMatchManager finishedMatchManager) {
        this.ongoingMatches = new OngoingMatches();
        this.matchEngine = matchEngine;
        this.finishedMatchManager = finishedMatchManager;
    }


    public Match generateMatch(String player1, String player2) {
        Match match = new Match(new Player(player1), new Player(player2));
        ongoingMatches.save(match);
        return match;
    }

    public Match addPointsTo(Match match, Player player) {
        Match currentMatch = ongoingMatches.findById(match.getUuid()).orElseThrow(() -> new MatchNotExistException("Match with that id: (" + player.getId() + ") isn't exist." ));
        matchEngine.calculation(player, currentMatch.getMatchScore());

        if (currentMatch.getMatchScore().getWinnerName() != null
                && currentMatch.getMatchScore().getWinnerName().equals(player.getName())){
            match.setWinner(player);
            finishedMatchManager.save(match);
        }

        return currentMatch;
    }
}
