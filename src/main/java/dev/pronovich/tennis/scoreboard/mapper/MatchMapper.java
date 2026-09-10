package dev.pronovich.tennis.scoreboard.mapper;

import dev.pronovich.tennis.scoreboard.dto.response.CreatedMatchDto;
import dev.pronovich.tennis.scoreboard.dto.response.MatchResponseDto;
import dev.pronovich.tennis.scoreboard.dto.response.MatchScoreDto;
import dev.pronovich.tennis.scoreboard.model.Match;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface MatchMapper {


    @Mapping(source = "uuid", target = "id")
    CreatedMatchDto toCreatedMatchDto(Match match);

    MatchScoreDto toCreateMatchScore(Match match);

    MatchResponseDto toMatchDto(Match match);

    List<MatchResponseDto> toMatchResponseDto(List<Match> matchList);
}
