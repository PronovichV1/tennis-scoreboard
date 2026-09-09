package dev.pronovich.tennis.scoreboard.service;
import dev.pronovich.tennis.scoreboard.dao.MatchDaoImpl;
import dev.pronovich.tennis.scoreboard.exception.DatabaseException;
import dev.pronovich.tennis.scoreboard.model.Match;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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


    public List<Match> getAllMatchesBy(String playersName){
        /// change the comment for exception
        List<Match> result = matchDao.getAllMatchesFor(playersName);
        if (result.isEmpty()){
            throw new DatabaseException("The Player: " + playersName + "dose not have any matches");
        }
        return result;
    }

}
