package dev.pronovich.tennis.scoreboard.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter

public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private UUID uuid;
    @ManyToOne
    @JoinColumn(name="player1")
    private Player player1;
    @ManyToOne
    @JoinColumn(name="player2")
    private Player player2;
    @ManyToOne
    @JoinColumn(name="winner")
    private Player winner;

    public Match(Player player1, Player player2) {
        uuid = UUID.randomUUID();
        this.player1 = player1;
        this.player2 = player2;
    }


}
