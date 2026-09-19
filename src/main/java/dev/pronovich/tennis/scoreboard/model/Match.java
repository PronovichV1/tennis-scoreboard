package dev.pronovich.tennis.scoreboard.model;

import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "matches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Transient
    private UUID uuid;
    @ManyToOne
    @JoinColumn(name="player1")
    private Player player1;
    @ManyToOne
    @JoinColumn(name="player2")
    private Player player2;
    @Transient
    private MatchScore matchScore;
    @ManyToOne
    @JoinColumn(name="winner")
    private Player winner;

    public Match(Player player1, Player player2) {
        uuid = UUID.randomUUID();
        this.player1 = player1;
        this.player2 = player2;
        matchScore = new MatchScore(new PlayerScore(player1), new PlayerScore(player2));
    }
}
