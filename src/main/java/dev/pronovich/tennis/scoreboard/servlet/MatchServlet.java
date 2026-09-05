package dev.pronovich.tennis.scoreboard.servlet;

import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.dto.request.GenerateMatchDto;
import dev.pronovich.tennis.scoreboard.dto.request.PlayerNameDto;
import dev.pronovich.tennis.scoreboard.dto.response.CreatedMatchDto;
import dev.pronovich.tennis.scoreboard.dto.response.MatchScoreDto;
import dev.pronovich.tennis.scoreboard.exception.MatchNotExistException;
import dev.pronovich.tennis.scoreboard.mapper.MatchMapper;
import dev.pronovich.tennis.scoreboard.mapper.PlayerMapper;
import dev.pronovich.tennis.scoreboard.model.Match;
import dev.pronovich.tennis.scoreboard.model.Player;
import dev.pronovich.tennis.scoreboard.service.MatchManager;
import dev.pronovich.tennis.scoreboard.service.FinishedMatchManager;
import dev.pronovich.tennis.scoreboard.service.OngoingMatches;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @ResponseStatus(HttpStatus.CREATED)
    public MatchScoreDto putPoints(@PathVariable UUID uuid, @RequestBody PlayerNameDto playerNameDto){
        /// CHANGE LATER
       Match match = Optional.of(ongoingMatches.findById(uuid)).get().orElseThrow(() -> new MatchNotExistException("Match with that id: (" + uuid + ") isn't exist." ));
       Player player = playerMapper.toPlayerEntity(playerNameDto);
       Match afterCalculation = matchManager.addPointsTo(match, player);
       MatchScoreDto response = matchMapper.toCreateMatchScore(afterCalculation);
       return response;
    }

    @GetMapping("/matches/{uuid}")
    @ResponseStatus(HttpStatus.CREATED)
    public MatchScoreDto getMatchScore(@PathVariable UUID uuid){
        P
        Match match = Optional.of(ongoingMatches.findById(uuid)).get().orElseThrow(() -> new MatchNotExistException("Match with that id: (" + uuid + ") isn't exist." ));


    }




    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedMatchDto createMatch(@RequestBody GenerateMatchDto generatedMatch){
        String firstPlayer = generatedMatch.firstPlayerName();
        String secondPlayer = generatedMatch.secondPlayerName();
        Match match = matchManager.generateMatch(firstPlayer, secondPlayer);
        CreatedMatchDto createdMatchDto = matchMapper.toCreatedMatchDto(match);
        System.out.println(createdMatchDto.id());
        return createdMatchDto;
    }

}
