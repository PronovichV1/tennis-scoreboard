package dev.pronovich.tennis.scoreboard.service;

import dev.pronovich.tennis.scoreboard.exception.InvalidFormatException;
import dev.pronovich.tennis.scoreboard.exception.MatchNotExistException;
import dev.pronovich.tennis.scoreboard.model.Match;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class OngoingMatches implements OngoingMatchesStorage{

    private HashMap<UUID, Match> matchesMap = new HashMap<>();

    @Override
    public void save(Match match) {
        matchesMap.computeIfAbsent(match.getUuid(),k -> match);
    }

    @Override
    public Optional<Match> findById(UUID id) {
        return Optional.of(matchesMap.get(id));
    }

    @Override
    public void remove(UUID id) {

    }
}
