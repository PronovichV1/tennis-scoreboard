package dev.pronovich.tennis.scoreboard.service;

import dev.pronovich.tennis.scoreboard.domain.ScoreEngine;
import dev.pronovich.tennis.scoreboard.model.Match;
import dev.pronovich.tennis.scoreboard.model.Player;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

@Service
public class MatchManager {

    private OngoingMatches ongoingMatches = new OngoingMatches();
    private ScoreEngine scoreEngine;

    public MatchManager(ScoreEngine scoreEngine) {
        this.scoreEngine = scoreEngine;
    }


    public Match generateMatch(String player1, String player2) {
        Match match = new Match(new Player(player1), new Player(player2));
        ongoingMatches.save(match);
        return match;
    }

    public void addPointsTo(Match match, Player player) {
        Match currentMatch = ongoingMatches.findById(match.getUuid());
    }
}
