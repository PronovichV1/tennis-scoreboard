package dev.pronovich.tennis.scoreboard.service;

import dev.pronovich.tennis.scoreboard.domain.engine.MatchEngine;
import dev.pronovich.tennis.scoreboard.exception.MatchNotExistException;
import dev.pronovich.tennis.scoreboard.model.Match;
import dev.pronovich.tennis.scoreboard.model.Player;
import org.springframework.stereotype.Service;

@Service
public class MatchManager {

    /// Add FinishedMatchManager implement logic inside add points
    /// when player win a match triger specific method with DAO and send this match to DB.


    private OngoingMatches ongoingMatches = new OngoingMatches();
    private MatchEngine matchEngine;
    private FinishedMatchManager finishedMatchManager;

    public MatchManager(MatchEngine matchEngine) {
        this.matchEngine = matchEngine;
    }


    public Match generateMatch(String player1, String player2) {
        Match match = new Match(new Player(player1), new Player(player2));
        ongoingMatches.save(match);
        return match;
    }

    public Match addPointsTo(Match match, Player player) {

        Match currentMatch = ongoingMatches.findById(match.getUuid()).orElseThrow(() -> new MatchNotExistException("Match with that id: (" + player.getId() + ") isn't exist." ));
        matchEngine.calculation(player, currentMatch.getMatchScore());
        if (match.getWinner() != null){
            finishedMatchManager.save(match);
        }

        return currentMatch;
    }
}
