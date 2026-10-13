package dev.pronovich.tennis.scoreboard.domain.engine;

import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.domain.model.TennisPlayer;

import java.util.Optional;

public interface Engine {

    Optional<TennisPlayer> calculation(TennisPlayer player, MatchScore matchScore);
}
