package dev.pronovich.tennis.scoreboard.service;

import dev.pronovich.tennis.scoreboard.model.Match;

import java.util.Optional;

public interface OngoingMatchesStorage {

     void save(Match match);
     Optional<Match> findById(int id);
     void remove(int id);

}
