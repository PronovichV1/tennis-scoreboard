package dev.pronovich.tennis.scoreboard.service;
import dev.pronovich.tennis.scoreboard.dao.MatchDaoImpl;
import dev.pronovich.tennis.scoreboard.domain.MatchesPage;
import dev.pronovich.tennis.scoreboard.exception.DatabaseException;
import dev.pronovich.tennis.scoreboard.model.Match;
import dev.pronovich.tennis.scoreboard.utils.MatchUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FinishedMatchManager {

    private final MatchDaoImpl matchDao;
    public FinishedMatchManager(MatchDaoImpl matchDao) {
        this.matchDao = matchDao;
    }

    @Transactional
    public Match save(Match match) {
        /// change the comment for exception
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
        return new MatchesPage(
                page,
                totalPages,
                matches
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
        return new MatchesPage(
                page,
                totalPages,
                matches
        );

    }
}
