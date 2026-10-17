package dev.pronovich.tennis.scoreboard.dto.response;

import java.util.List;

public record PlayerMatchesResponseDto(List<FinishedMatchResponseDto> matches, Integer currentPage,
                                       Integer totalPages) {

}
