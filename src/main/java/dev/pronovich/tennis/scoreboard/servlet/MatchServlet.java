package dev.pronovich.tennis.scoreboard.servlet;

import dev.pronovich.tennis.scoreboard.dto.GenerateMatchDto;
import dev.pronovich.tennis.scoreboard.model.Match;
import dev.pronovich.tennis.scoreboard.model.Player;
import dev.pronovich.tennis.scoreboard.service.MatchManager;
import dev.pronovich.tennis.scoreboard.service.FinishedMatchManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/matches")
public class MatchServlet {

    private MatchManager matchManager;
    private FinishedMatchManager finishedMatchManager;

    public MatchServlet(MatchManager matchManager, FinishedMatchManager finishedMatchManager) {
        this.matchManager = matchManager;
        this.finishedMatchManager = finishedMatchManager;
    }


    @PostMapping
    protected UUID createMatch(@RequestBody GenerateMatchDto generatedMatch){
        String firstPlayer = generatedMatch.firstPlayerName();
        String secondPlayer = generatedMatch.secondPlayerName();
        Match match = matchManager.generateMatch(firstPlayer, secondPlayer);
        return match.getUuid();
    }

}
