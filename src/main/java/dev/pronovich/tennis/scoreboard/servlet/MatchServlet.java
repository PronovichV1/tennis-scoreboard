package dev.pronovich.tennis.scoreboard.servlet;

import dev.pronovich.tennis.scoreboard.dto.request.GenerateMatchDto;
import dev.pronovich.tennis.scoreboard.dto.request.PlayerMatchesRequestDto;
import dev.pronovich.tennis.scoreboard.dto.request.PlayerNameDto;
import dev.pronovich.tennis.scoreboard.dto.request.MatchUuidDto;
import dev.pronovich.tennis.scoreboard.dto.response.CreatedMatchDto;
import dev.pronovich.tennis.scoreboard.dto.response.MatchScoreResponseDto;
import dev.pronovich.tennis.scoreboard.dto.response.PlayerMatchesResponseDto;
import dev.pronovich.tennis.scoreboard.exception.MatchNotExistException;
import dev.pronovich.tennis.scoreboard.exception.PlayerNotExist;
import dev.pronovich.tennis.scoreboard.mapper.MatchMapper;
import dev.pronovich.tennis.scoreboard.mapper.PlayerMapper;
import dev.pronovich.tennis.scoreboard.model.Match;
import dev.pronovich.tennis.scoreboard.model.Player;
import dev.pronovich.tennis.scoreboard.service.MatchManager;
import dev.pronovich.tennis.scoreboard.service.FinishedMatchManager;
import dev.pronovich.tennis.scoreboard.service.OngoingMatches;
import dev.pronovich.tennis.scoreboard.utils.MatchUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/matches")
public class MatchServlet { /// change name to MatchController

    private MatchManager matchManager;
    private OngoingMatches ongoingMatches;
    private FinishedMatchManager finishedMatchManager;
    private MatchMapper matchMapper;
    private PlayerMapper playerMapper;

    public MatchServlet(MatchManager matchManager, OngoingMatches ongoingMatches, FinishedMatchManager finishedMatchManager, MatchMapper matchMapper,  PlayerMapper playerMapper) {
        this.matchManager = matchManager;
        this.ongoingMatches = ongoingMatches;
        this.finishedMatchManager = finishedMatchManager;
        this.matchMapper = matchMapper;
        this.playerMapper = playerMapper;
    }


    @PostMapping("/{uuid}/point")
    @ResponseStatus(HttpStatus.OK)
    public MatchScoreResponseDto putPoints(@PathVariable UUID uuid, @RequestBody PlayerNameDto playerNameDto){
        Match match = Optional.of(ongoingMatches.findById(uuid)).get()
                .orElseThrow(() -> new MatchNotExistException("Match with that id: (" + uuid + ") isn't exist." ));
        Player player = playerMapper.toPlayerEntity(playerNameDto);
        player.setUuid(match.getMatchScore().getUuidBy(player.getName()).orElseThrow(() -> new PlayerNotExist("Player with this uuid is not exist")));
       Match afterCalculation = matchManager.addPointsTo(match, player);
       MatchScoreResponseDto response = matchMapper.toCreateMatchScore(afterCalculation);
       return response;
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public MatchScoreResponseDto getMatchScore(@PathVariable UUID uuid){
        MatchUuidDto matchUuidDto = new MatchUuidDto(uuid);
        Match match = ongoingMatches.findById(matchUuidDto.uuid()).orElseThrow(() -> new MatchNotExistException("Match with that id: (" + uuid + ") isn't exist."));
        MatchScoreResponseDto response = matchMapper.toCreateMatchScore(match);
        return response;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedMatchDto createMatch(@RequestBody GenerateMatchDto generatedMatch){
        String firstPlayer = generatedMatch.firstPlayerName();
        String secondPlayer = generatedMatch.secondPlayerName();
        Match match = matchManager.generateMatch(firstPlayer, secondPlayer);
        CreatedMatchDto createdMatchDto = matchMapper.toCreatedMatchDto(match);
        return createdMatchDto;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PlayerMatchesResponseDto getAllMatches(@RequestParam (name = "page", required = false, defaultValue = "0") int page,
                                                  @RequestParam (name = "player_name", required = false) String playerName){

        PlayerMatchesRequestDto playerMatchesRequestDto = new PlayerMatchesRequestDto(page, playerName);
        List<Match> matchList;
        if (playerMatchesRequestDto.playerName().isBlank() || playerMatchesRequestDto.playerName().isEmpty()){
            matchList = finishedMatchManager.getAllMatches(playerMatchesRequestDto.page());
        } else {
            matchList = finishedMatchManager.getAllMatchesBy(playerMatchesRequestDto.page(), playerName);
        }
        int totalPages = matchList.size() / MatchUtils.DEFAULT_PAGE_SIZE;
        PlayerMatchesResponseDto playerMatchesResponseDto = new PlayerMatchesResponseDto(
                matchMapper.toMatchResponseDto(matchList), page, totalPages);

        return playerMatchesResponseDto;
    }

}
