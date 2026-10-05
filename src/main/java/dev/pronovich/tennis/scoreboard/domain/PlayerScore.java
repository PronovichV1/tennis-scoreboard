package dev.pronovich.tennis.scoreboard.domain;


import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class PlayerScore {

    private TennisPlayer player;
    private Points points;
    private Integer games;
    private Integer sets;
    private Integer tieBreakPoints;

    public PlayerScore(TennisPlayer player) {
        this.player = player;
        points = Points.ZERO;
        games = 0;
        sets = 0;
        tieBreakPoints = null;
    }


    public void addPoints() {
       this.points = points.nextPoints();
    }

    public void addGamesScore(){
        games++;
    }

    public void resetGames(){
        games = 0;
    }


    public void addSetsScore(){
        sets++;
    }

    public void resetPoints(){
        this.points = Points.ZERO;
    }

    public void addTieBreakPoints() {
        tieBreakPoints++;
    }
}
