package dev.pronovich.tennis.scoreboard.servlet;

import dev.pronovich.tennis.scoreboard.dto.request.GenerateMatchDto;
import dev.pronovich.tennis.scoreboard.dto.response.CreatedMatchDto;
import dev.pronovich.tennis.scoreboard.mapper.MatchMapper;
import dev.pronovich.tennis.scoreboard.model.Match;
import dev.pronovich.tennis.scoreboard.service.MatchManager;
import dev.pronovich.tennis.scoreboard.service.FinishedMatchManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/matches")
public class MatchServlet {

    private MatchManager matchManager;
    private FinishedMatchManager finishedMatchManager;
    private MatchMapper matchMapper;

    public MatchServlet(MatchManager matchManager, FinishedMatchManager finishedMatchManager, MatchMapper matchMapper) {
        this.matchManager = matchManager;
        this.finishedMatchManager = finishedMatchManager;
        this.matchMapper = matchMapper;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    protected CreatedMatchDto createMatch(@RequestBody GenerateMatchDto generatedMatch){
        String firstPlayer = generatedMatch.firstPlayerName();
        String secondPlayer = generatedMatch.secondPlayerName();
        Match match = matchManager.generateMatch(firstPlayer, secondPlayer);
        CreatedMatchDto createdMatchDto = matchMapper.toCreatedMatchDto(match);
        return createdMatchDto;
    }

}
