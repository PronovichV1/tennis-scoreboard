package dev.pronovich.tennis.scoreboard.mapper;

import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
import dev.pronovich.tennis.scoreboard.domain.Points;
import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.dto.response.*;
import dev.pronovich.tennis.scoreboard.model.Match;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface MatchMapper {


    @Mapping(source = "uuid", target = "id")
    CreatedMatchDto toCreatedMatchDto(MatchScore matchScore);

    @Mapping(source = "player.name", target = "name")
    PlayerScoreResponseDto toPlayerScoreDto(PlayerScore playerScore);

    @Mapping(source = "matchScore.playerScore1", target = "firstPlayer")
    @Mapping(source = "matchScore.playerScore2", target = "secondPlayer")
    @Mapping(source = "winner.name", target = "winnerName")
    MatchScoreResponseDto toCreateMatchScore(Match match);

    @Mapping(source = "player1.name", target = "firstPlayerName")
    @Mapping(source = "player2.name", target = "secondPlayerName")
    @Mapping(source = "winner.name", target = "winnerName")
    FinishedMatchResponseDto toMatchResponseDto(Match match);

    List<FinishedMatchResponseDto> toMatchResponseDtoList(List<Match> matchList);

    default String mapPoints(Points points){
        return points != null ? points.getCode() : null;
    }
}
