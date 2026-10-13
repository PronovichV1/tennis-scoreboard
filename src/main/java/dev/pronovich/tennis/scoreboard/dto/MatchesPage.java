package dev.pronovich.tennis.scoreboard.dto;

import dev.pronovich.tennis.scoreboard.domain.model.TennisMatch;
import dev.pronovich.tennis.scoreboard.model.Match;

import java.util.List;

public record MatchesPage(Integer currentPage, Integer totalMatches, List<TennisMatch> matchList) {
}
