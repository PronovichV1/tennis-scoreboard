package dev.pronovich.tennis.scoreboard.dao;

import dev.pronovich.tennis.scoreboard.model.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
=======
import java.util.List;
>>>>>>> refactoring
import java.util.Optional;

@Repository
public class PlayerDaoImpl {

    private SessionFactory sessionFactory;

    public PlayerDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

<<<<<<< HEAD
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
=======
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
>>>>>>> refactoring
        }
    }
}
