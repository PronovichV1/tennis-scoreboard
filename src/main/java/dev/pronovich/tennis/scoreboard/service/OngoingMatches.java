package dev.pronovich.tennis.scoreboard.service;

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
    public Optional<Match> findById(UUID id) {
        return Optional.of(matchesMap.get(id));
    }

    @Override
    public void remove(UUID id) {
    }

    /// test method
    public void showAll(){
        matchesMap.forEach((uuid,m) -> System.out.println(m.toString()));
    }
}
