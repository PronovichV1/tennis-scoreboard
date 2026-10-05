package dev.pronovich.tennis.scoreboard.mapper;

import dev.pronovich.tennis.scoreboard.domain.TennisPlayer;
import dev.pronovich.tennis.scoreboard.dto.request.PlayerNameDto;
import dev.pronovich.tennis.scoreboard.model.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    @Mapping(source = "name", target = "name")
    Player toPlayerEntity(PlayerNameDto playerNameDto);

    TennisPlayer toTennisPlayer(Player player);
}
