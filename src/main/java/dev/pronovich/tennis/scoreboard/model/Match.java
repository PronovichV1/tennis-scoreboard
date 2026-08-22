package dev.pronovich.tennis.scoreboard.model;

import jakarta.persistence.*;

@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name="player1")
    private Player player1;
    @ManyToOne
    @JoinColumn(name="player2")
    private Player player2;
    @ManyToOne
    @JoinColumn(name="winner")
    private Player winner;

}
