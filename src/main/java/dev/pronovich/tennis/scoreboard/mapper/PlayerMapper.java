package dev.pronovich.tennis.scoreboard.mapper;

import dev.pronovich.tennis.scoreboard.domain.model.TennisMatch;
import dev.pronovich.tennis.scoreboard.domain.model.TennisPlayer;
import dev.pronovich.tennis.scoreboard.dto.request.PlayerNameDto;
import dev.pronovich.tennis.scoreboard.model.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    @Mapping(source = "name", target = "name")
    Player toPlayerEntity(PlayerNameDto playerNameDto);

    /// new one | for DDD
    Player toPlayerEntity(TennisPlayer tennisPlayer);

    @Mapping(target = "uuid", expression = "java(java.util.UUID.randomUUID())")
    TennisPlayer toTennisPlayer(Player player);
}
