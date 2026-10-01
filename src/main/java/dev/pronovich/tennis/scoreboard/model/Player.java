package dev.pronovich.tennis.scoreboard.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "names")
@Getter
@Setter
@NoArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="name", length = 50)
    private String name;
    @Transient
    private UUID uuid;

    public Player(String name) {
        this.name = name;
        uuid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if (obj == null || getClass() != obj.getClass()){
            return false;
        }
        Player player = (Player) obj;
        return Objects.equals(this.name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
