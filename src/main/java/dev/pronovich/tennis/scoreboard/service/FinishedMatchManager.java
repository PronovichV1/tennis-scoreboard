package dev.pronovich.tennis.scoreboard.service;
import dev.pronovich.tennis.scoreboard.dao.MatchDaoImpl;
import dev.pronovich.tennis.scoreboard.dao.PlayerDaoImpl;
import dev.pronovich.tennis.scoreboard.domain.model.TennisMatch;
import dev.pronovich.tennis.scoreboard.dto.MatchesPage;
import dev.pronovich.tennis.scoreboard.exception.DatabaseException;
import dev.pronovich.tennis.scoreboard.mapper.MatchMapper;
import dev.pronovich.tennis.scoreboard.mapper.PlayerMapper;
import dev.pronovich.tennis.scoreboard.model.Match;
import dev.pronovich.tennis.scoreboard.model.Player;
import dev.pronovich.tennis.scoreboard.utils.MatchUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FinishedMatchManager {

    private final MatchDaoImpl matchDao;
    private MatchMapper matchMapper;
    private PlayerDaoImpl playerDao;
    public FinishedMatchManager(MatchDaoImpl matchDao,MatchMapper matchMapper, PlayerDaoImpl playerDao) {
        this.matchDao = matchDao;
        this.matchMapper = matchMapper;
        this.playerDao = playerDao;
    }

    @Transactional
    public Match save(Match match) {
        prepareExistingPlayers(match);
        Match matchAfterDao = matchDao.save(match).orElseThrow(() -> new DatabaseException("Match didn't saved by Database"));
       return matchAfterDao;
    }


    public MatchesPage getAllMatchesBy(Integer page, String playersName){
        int pageSize = MatchUtils.DEFAULT_PAGE_SIZE;
        int fromIndex = (page - 1) * pageSize;
        List<Match> allMatches = matchDao.getAllMatchesFor(playersName, pageSize, fromIndex);
        if (allMatches.isEmpty()){
            throw new DatabaseException("The Player " + playersName  + " doesn't have any match yet");
        }
        int totalMatches = allMatches.size();
        int toIndex = Math.min(fromIndex + pageSize, totalMatches);
        int totalPages = (totalMatches + pageSize - 1) / pageSize;
        List<Match> matches = allMatches.subList(fromIndex, toIndex);
        List<TennisMatch> tennisMatches = matchMapper.toTennisMatchList(matches);

        return new MatchesPage(
                page,
                totalPages,
                tennisMatches
        );
    }

    public MatchesPage getAllMatches(Integer page) {
        int pageSize = MatchUtils.DEFAULT_PAGE_SIZE;
        int fromIndex = (page - 1) * pageSize;
        List<Match> allMatches = matchDao.getAllMatches();
        int totalMatches = allMatches.size();
        if (allMatches.isEmpty()){
            throw new DatabaseException("Database doesn't have any match yet.");
        }
        int toIndex = Math.min(fromIndex + pageSize, totalMatches);
        int totalPages = (totalMatches + pageSize - 1) / pageSize;
        List<Match> matches = allMatches.subList(fromIndex, toIndex);
        List<TennisMatch> tennisMatches = matchMapper.toTennisMatchList(matches);
        return new MatchesPage(
                page,
                totalPages,
                tennisMatches
        );

    }

    private void prepareExistingPlayers(Match match){
        List<String> playerList = List.of(
                match.getPlayer1().getName(),
                match.getPlayer2().getName()
        );

        List<Player> existing = playerDao.findByNames(playerList);
        Map<String,Player> map = existing.stream().collect(Collectors.toMap(Player::getName, p->p));


        if (map.containsKey(match.getPlayer1().getName())){
            match.setPlayer1(map.get(match.getPlayer1().getName()));
        }
        if (map.containsKey(match.getPlayer2().getName())){
            match.setPlayer2(map.get(match.getPlayer2().getName()));
        }
        if (match.getWinner() != null && map.containsKey(match.getWinner().getName())) {
            match.setWinner(map.get(match.getWinner().getName()));
        }
    }
}
