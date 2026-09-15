package dev.pronovich.tennis.scoreboard.mapper;

import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
import dev.pronovich.tennis.scoreboard.domain.Points;
import dev.pronovich.tennis.scoreboard.dto.response.CreatedMatchDto;
import dev.pronovich.tennis.scoreboard.dto.response.MatchResponseDto;
import dev.pronovich.tennis.scoreboard.dto.response.MatchScoreResponseDto;
import dev.pronovich.tennis.scoreboard.dto.response.PlayerScoreResponseDto;
import dev.pronovich.tennis.scoreboard.model.Match;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.awt.*;
import java.util.List;


@Mapper(componentModel = "spring")
public interface MatchMapper {


    @Mapping(source = "uuid", target = "id")
    CreatedMatchDto toCreatedMatchDto(Match match);

    @Mapping(source = "player.name", target = "name")
    PlayerScoreResponseDto toPlayerScoreDto(PlayerScore playerScore);

    @Mapping(source = "matchScore.playerScore1", target = "firstPlayerScoreResponseDto")
    @Mapping(source = "matchScore.playerScore2", target = "secondPlayerScoreResponseDto")
    @Mapping(source = "winner.name", target = "winnerName")
    MatchScoreResponseDto toCreateMatchScore(Match match);

    MatchResponseDto toMatchDto(Match match);

    List<MatchResponseDto> toMatchResponseDto(List<Match> matchList);

    default String mapPoints(Points points){
        return points != null ? points.getCode() : null;
    }
}
