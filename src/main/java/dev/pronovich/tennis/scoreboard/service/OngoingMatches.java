package dev.pronovich.tennis.scoreboard.service;

import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.exception.MatchNotExistException;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OngoingMatches implements OngoingMatchesStorage {

    private static final ConcurrentHashMap<UUID, MatchScore> matchesMap = new ConcurrentHashMap<>();

    @Override
    public void save(MatchScore match) {
        matchesMap.computeIfAbsent(match.getUuid(), k -> match);
    }

    @Override
    public MatchScore findById(UUID id) {
        MatchScore match = matchesMap.get(id);
        if (match == null) {
            throw new MatchNotExistException(
                    "Match with that id: (" + id.toString() + ") isn't exist."
            );
        }
        return match;
    }

    @Override
    public void remove(UUID id) {
        if (matchesMap.get(id) == null) {
            throw new MatchNotExistException(
                    "Match with that id: (" + id.toString() + ") isn't exist."
            );
        }
        matchesMap.remove(id);
    }

}
