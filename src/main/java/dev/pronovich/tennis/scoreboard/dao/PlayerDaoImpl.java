package dev.pronovich.tennis.scoreboard.dao;

import dev.pronovich.tennis.scoreboard.model.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public class PlayerDaoImpl {

    private SessionFactory sessionFactory;

    public PlayerDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public List<Player> findByNames(List<String> names){
        if (names == null || names.isEmpty()){
            return List.of();
        }
        try (Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction();
            List<Player> players = session.
                    createSelectionQuery(
                            "FROM Player p WHERE p.name IN (:names)", Player.class)
                    .setParameter("names", names)
                    .getResultList();
            tx.commit();
            return players;
        }
    }
}
