package dev.pronovich.tennis.scoreboard.dao;

import dev.pronovich.tennis.scoreboard.model.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PlayerDaoImpl {

    private SessionFactory sessionFactory;

    public PlayerDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<Player> findByName(String name){
        try (Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction();
            Optional<Player> player = session.
                    createSelectionQuery(
                            "FROM Player p WHERE p.name = :name", Player.class)
                    .setParameter("name", name)
                    .uniqueResultOptional();
            tx.commit();
            return player;
        }
    }
}
