package dev.pronovich.tennis.scoreboard.servlet;

import dev.pronovich.tennis.scoreboard.dto.request.GenerateMatchDto;
import dev.pronovich.tennis.scoreboard.dto.request.PlayerMatchesRequestDto;
import dev.pronovich.tennis.scoreboard.dto.request.PlayerNameDto;
import dev.pronovich.tennis.scoreboard.dto.request.MatchUuidDto;
import dev.pronovich.tennis.scoreboard.dto.response.CreatedMatchDto;
import dev.pronovich.tennis.scoreboard.dto.response.MatchResponseDto;
import dev.pronovich.tennis.scoreboard.dto.response.MatchScoreDto;
import dev.pronovich.tennis.scoreboard.dto.response.PlayerMatchesResponseDto;
import dev.pronovich.tennis.scoreboard.exception.MatchNotExistException;
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
public class MatchServlet {

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
    @ResponseStatus(HttpStatus.CREATED) /// check status
    public MatchScoreDto putPoints(@PathVariable UUID uuid, @RequestBody PlayerNameDto playerNameDto){
        /// add dto for response
       Match match = Optional.of(ongoingMatches.findById(uuid)).get().orElseThrow(() -> new MatchNotExistException("Match with that id: (" + uuid + ") isn't exist." ));
       Player player = playerMapper.toPlayerEntity(playerNameDto);
       Match afterCalculation = matchManager.addPointsTo(match, player);
       MatchScoreDto response = matchMapper.toCreateMatchScore(afterCalculation);
       return response;
    }

    @GetMapping("/matches/{uuid}")
    @ResponseStatus(HttpStatus.CREATED) /// check status
    public MatchScoreDto getMatchScore(@PathVariable UUID uuid){
        MatchUuidDto matchUuidDto = new MatchUuidDto(uuid);
        Match match = Optional.of(ongoingMatches.findById(matchUuidDto.uuid())).get().orElseThrow(() -> new MatchNotExistException("Match with that id: (" + uuid + ") isn't exist." ));
        MatchScoreDto response = matchMapper.toCreateMatchScore(match);
        return response;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) /// check status
    public CreatedMatchDto createMatch(@RequestBody GenerateMatchDto generatedMatch){
        String firstPlayer = generatedMatch.firstPlayerName();
        String secondPlayer = generatedMatch.secondPlayerName();
        Match match = matchManager.generateMatch(firstPlayer, secondPlayer);
        CreatedMatchDto createdMatchDto = matchMapper.toCreatedMatchDto(match);
        System.out.println(createdMatchDto.id());
        return createdMatchDto;
    }

    @GetMapping("/matches")
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
