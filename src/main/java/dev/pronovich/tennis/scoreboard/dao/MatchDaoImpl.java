package dev.pronovich.tennis.scoreboard.dao;

import dev.pronovich.tennis.scoreboard.exception.MatchNotExistException;
import dev.pronovich.tennis.scoreboard.model.Match;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public class MatchDaoImpl implements BaseDao<Match>{

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Optional<Match> save(Match match) {
       Match dbMatch = entityManager.merge(match);
       /// add check
       return Optional.of(dbMatch);
    }
}
