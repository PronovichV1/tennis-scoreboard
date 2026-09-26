package dev.pronovich.tennis.scoreboard.service;


import dev.pronovich.tennis.scoreboard.domain.engine.MatchEngine;
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
    public MatchManager(OngoingMatches ongoingMatches, FinishedMatchManager finishedMatchManager) {
        this.ongoingMatches = ongoingMatches;
        this.matchEngine = new MatchEngine();
        this.finishedMatchManager = finishedMatchManager;
    }


    public Match generateMatch(String player1, String player2) {
        Match match = new Match(new Player(player1), new Player(player2));
        ongoingMatches.save(match);
        return match;
    }

    public Match addPointsTo(Match match, Player player) {
        Match currentMatch = ongoingMatches.findById(match.getUuid());
        matchEngine.calculation(player, currentMatch.getMatchScore());

        if (currentMatch.getMatchScore().getWinner() != null) {
            currentMatch.setWinner(currentMatch.getMatchScore().getWinner());
            finishedMatchManager.save(match);
            ongoingMatches.remove(match.getUuid());
        }
        return currentMatch;
    }
}
