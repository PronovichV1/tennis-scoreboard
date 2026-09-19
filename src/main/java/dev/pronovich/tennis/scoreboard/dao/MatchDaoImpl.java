package dev.pronovich.tennis.scoreboard.dao;

import dev.pronovich.tennis.scoreboard.model.Match;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public class MatchDaoImpl implements BaseDao<Match>{

    SessionFactory sessionFactory;

    @Autowired
    public MatchDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Match> save(Match match) {
       try (Session session = sessionFactory.openSession()){
           Transaction tx = session.beginTransaction();
           session.persist(match.getPlayer1());
           session.persist(match.getPlayer2());
           System.out.println("player1 ID = " + match.getPlayer1().getId());
           System.out.println("player2 ID = " + match.getPlayer2().getId());
           System.out.println("winner ID = " + match.getWinner().getId());

           System.out.println("player1 == winner: " +
                   (match.getPlayer1() == match.getWinner()));

           System.out.println("player2 == winner: " +
                   (match.getPlayer2() == match.getWinner()));
           Match savedMatch = session.merge(match);
           tx.commit();
           return Optional.of(savedMatch);
       }
    }


    public List<Match> getAllMatchesFor(String playersName, int pageSize, int offset) {
        try (Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction();

            List<Match> matchList = session.createQuery(
                    "SELECT m FROM Match m WHERE LOWER(m.player1.name) LIKE LOWER(:name) " +
                            "OR LOWER(m.player2.name) LIKE LOWER(:name)", Match.class)
                    .setParameter("name", playersName)
                    .setFirstResult(offset)
                    .setMaxResults(pageSize)
                    .getResultList();

            tx.commit();
            return matchList;
        }
    }

    public List<Match> getAllMatches(int pageSize, int offset) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            List<Match> matchList = session.createQuery("SELECT m FROM Match m", Match.class)
                    .setFirstResult(offset)
                    .setMaxResults(pageSize)
                    .getResultList();

            tx.commit();
            return matchList;
        }
    }
}
