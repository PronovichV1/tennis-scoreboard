package dev.pronovich.tennis.scoreboard.servlet;

import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.domain.model.TennisPlayer;
import dev.pronovich.tennis.scoreboard.dto.MatchesPage;
import dev.pronovich.tennis.scoreboard.dto.request.GenerateMatchDto;
import dev.pronovich.tennis.scoreboard.dto.request.PlayerNameDto;
import dev.pronovich.tennis.scoreboard.dto.request.MatchUuidDto;
import dev.pronovich.tennis.scoreboard.dto.response.CreatedMatchDto;
import dev.pronovich.tennis.scoreboard.dto.response.FinishedMatchResponseDto;
import dev.pronovich.tennis.scoreboard.dto.response.MatchScoreResponseDto;
import dev.pronovich.tennis.scoreboard.dto.response.PlayerMatchesResponseDto;
import dev.pronovich.tennis.scoreboard.mapper.MatchMapper;
import dev.pronovich.tennis.scoreboard.mapper.PlayerMapper;
import dev.pronovich.tennis.scoreboard.model.Match;
import dev.pronovich.tennis.scoreboard.service.MatchManager;
import dev.pronovich.tennis.scoreboard.service.FinishedMatchManager;
import dev.pronovich.tennis.scoreboard.service.OngoingMatches;
import dev.pronovich.tennis.scoreboard.validator.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/matches")
public class MatchServlet {

    private MatchManager matchManager;
    private OngoingMatches ongoingMatches;
    private FinishedMatchManager finishedMatchManager;
    private MatchMapper matchMapper;
    private PlayerMapper playerMapper;
    private Validator validation;

    public MatchServlet(MatchManager matchManager, OngoingMatches ongoingMatches, FinishedMatchManager finishedMatchManager, MatchMapper matchMapper,  PlayerMapper playerMapper, Validator validation) {
        this.matchManager = matchManager;
        this.ongoingMatches = ongoingMatches;
        this.finishedMatchManager = finishedMatchManager;
        this.matchMapper = matchMapper;
        this.playerMapper = playerMapper;
        this.validation = validation;
    }


    @PostMapping("/{uuid}/point")
    @ResponseStatus(HttpStatus.OK)
    public MatchScoreResponseDto putPoints(@PathVariable UUID uuid, @RequestBody PlayerNameDto playerNameDto){
        String playerName = playerNameDto.name();
        MatchScore matchScore = ongoingMatches.findById(uuid);
        TennisPlayer tennisPlayer = matchScore.getTennisPlayerBy(playerName);
        MatchScore afterCalculation = matchManager.addPointsTo(matchScore, tennisPlayer);
        MatchScoreResponseDto response = matchMapper.toCreateMatchScore(afterCalculation);
       return response;
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public MatchScoreResponseDto getMatchScore(@PathVariable UUID uuid){
        MatchUuidDto matchUuidDto = new MatchUuidDto(uuid);
        Match match = ongoingMatches.findById(matchUuidDto.uuid());
        MatchScoreResponseDto response = matchMapper.toCreateMatchScore(match);
        return response;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedMatchDto createMatch(@RequestBody GenerateMatchDto generatedMatch){
        validation.validate(generatedMatch);
        MatchScore matchScore = matchManager.generateMatch(generatedMatch.firstPlayerName(), generatedMatch.secondPlayerName());
        CreatedMatchDto createdMatchDto = matchMapper.toCreatedMatchDto(matchScore);
        return createdMatchDto;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PlayerMatchesResponseDto getAllMatches(@RequestParam (name = "page", required = false, defaultValue = "1") int page,
                                                  @RequestParam (name = "player_name", required = false) String playerName){

        MatchesPage matchesPage = null;
        if (playerName == null || playerName.isBlank()){
            matchesPage = finishedMatchManager.getAllMatches(page);
        } else {
            matchesPage = finishedMatchManager.getAllMatchesBy(page, playerName);
        }
        List<FinishedMatchResponseDto> matches = matchMapper.toMatchResponseDtoList(matchesPage.matchList());
        return new PlayerMatchesResponseDto(
                matches,
                matchesPage.currentPage(),
                matchesPage.totalMatches()
        );
    }

}
