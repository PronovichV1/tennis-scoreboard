package dev.pronovich.tennis.scoreboard.service;

import dev.pronovich.tennis.scoreboard.model.Match;
import dev.pronovich.tennis.scoreboard.model.Player;
import org.springframework.stereotype.Service;

@Service
public class MatchManager {

    private OngoingMatches ongoingMatches = new OngoingMatches();


    public Match generateMatch(String player1, String player2) {
        Match match = new Match(new Player(player1), new Player(player2));
        ongoingMatches.save(match);
        return match;
    }
}
