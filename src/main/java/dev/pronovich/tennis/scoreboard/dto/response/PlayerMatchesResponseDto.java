package dev.pronovich.tennis.scoreboard.dto.response;

import java.util.List;

public record PlayerMatchesResponseDto(List<MatchResponseDto> matches, int currentPage, int totalPages) {

}
