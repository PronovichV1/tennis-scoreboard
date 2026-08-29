package dev.pronovich.tennis.scoreboard.domain.engene;

import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.model.Player;

public interface Engine {

    void calculation(Player player, MatchScore matchScore);
}
