package dev.pronovich.tennis.scoreboard.service;
import dev.pronovich.tennis.scoreboard.dao.MatchDaoImpl;
import dev.pronovich.tennis.scoreboard.dao.PlayerDaoImpl;
import dev.pronovich.tennis.scoreboard.domain.model.TennisMatch;
import dev.pronovich.tennis.scoreboard.dto.MatchesPage;
import dev.pronovich.tennis.scoreboard.exception.DatabaseException;
import dev.pronovich.tennis.scoreboard.mapper.MatchMapper;
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


    public MatchesPage getAllMatchesForSpecificPlayer(Integer page, String playersName){
        int pageSize = MatchUtils.DEFAULT_PAGE_SIZE;

        int offset = (page - 1) * pageSize;
        long totalMatches = matchDao.countAllMatchesForSpecificPlayer(playersName);
        int totalPages = (int) Math.ceil ((double) totalMatches / pageSize);
        List<Match> matches = matchDao.getAllMatchesFor(playersName, offset, pageSize);
        List<TennisMatch> tennisMatches = matchMapper.toTennisMatchList(matches);
        return new MatchesPage(
                page,
                totalPages,
                tennisMatches
        );
    }

    public MatchesPage getAllMatches(Integer page) {
        int pageSize = MatchUtils.DEFAULT_PAGE_SIZE;
        int offset = (page - 1) * pageSize;
        long totalMatches = matchDao.countAllMatches();
        int totalPages = (int) Math.ceil ((double) totalMatches / pageSize);
        List<Match> matches = matchDao.getAllMatches(offset, pageSize);
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
        Map<String, Player> map = existing.stream().collect(Collectors.toMap(Player::getName, p -> p));

        Player p1 = map.computeIfAbsent(match.getPlayer1().getName(), Player::new);
        Player p2 = map.computeIfAbsent(match.getPlayer2().getName(), Player::new);

        match.setPlayer1(p1);
        match.setPlayer2(p2);

        if (match.getWinner() != null) {
            match.setWinner(map.computeIfAbsent(match.getWinner().getName(), Player::new));
        }
    }
}
