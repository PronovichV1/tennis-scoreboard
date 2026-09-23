package dev.pronovich.tennis.scoreboard.service;

import dev.pronovich.tennis.scoreboard.exception.MatchNotExistException;
import dev.pronovich.tennis.scoreboard.model.Match;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OngoingMatches implements OngoingMatchesStorage{

    private static final HashMap<UUID, Match> matchesMap = new HashMap<>();

    @Override
    public void save(Match match) {
        matchesMap.computeIfAbsent(match.getUuid(),k -> match);
    }

    @Override
    public Match findById(UUID id) {
        Match match = matchesMap.get(id);
        if (match == null){
           throw new MatchNotExistException(
                   "Match with that id: (" + id.toString() + ") isn't exist."
           );
        }
        return match;
    }

    @Override
    public void remove(UUID id) {
        if (matchesMap.get(id) == null){
            throw new MatchNotExistException(
                    "Match with that id: (" + id.toString() + ") isn't exist."
            );
        }
        matchesMap.remove(id);
    }

}
