package dev.pronovich.tennis.scoreboard.domain.engine;

import dev.pronovich.tennis.scoreboard.domain.TennisMatch;
import dev.pronovich.tennis.scoreboard.model.Player;

import java.util.Optional;

public interface Engine {

    Optional<Player> calculation(Player player, TennisMatch tennisMatch);
}
