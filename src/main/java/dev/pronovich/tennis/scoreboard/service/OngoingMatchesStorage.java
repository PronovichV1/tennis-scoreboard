package dev.pronovich.tennis.scoreboard.service;

import dev.pronovich.tennis.scoreboard.domain.MatchScore;

import java.util.UUID;

public interface OngoingMatchesStorage {

     void save(MatchScore match);
     MatchScore findById(UUID id);
     void remove(UUID id);

}
