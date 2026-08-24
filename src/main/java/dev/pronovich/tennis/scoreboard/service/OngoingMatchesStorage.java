package dev.pronovich.tennis.scoreboard.service;

import dev.pronovich.tennis.scoreboard.model.Match;

import java.util.Optional;
import java.util.UUID;

public interface OngoingMatchesStorage {

     void save(Match match);
     Optional<Match> findById(UUID id);
     void remove(UUID id);

}
