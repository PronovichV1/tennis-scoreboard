package dev.pronovich.tennis.scoreboard.domain;

import dev.pronovich.tennis.scoreboard.model.Match;

import java.util.List;

public record MatchesPage(int currentPage, int totalMatches, List<Match> matchList) {
}
