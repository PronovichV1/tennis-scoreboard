package dev.pronovich.tennis.scoreboard.dao;

import dev.pronovich.tennis.scoreboard.exception.MatchNotExistException;
import dev.pronovich.tennis.scoreboard.model.Match;
import dev.pronovich.tennis.scoreboard.model.Player;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;


import java.util.List;
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


    public List<Match> getAllMatchesFor(String playersName) {
        /// check the string before put it inside query
        return entityManager.createQuery(
                "SELECT m FROM Match m WHERE LOWER(m.player1.name) LIKE LOWER(:name) " +
                        "OR LOWER(m.player2.name) LIKE LOWER(:name)", Match.class)
                .setParameter("name", playersName)
                .getResultList();
    }
}
