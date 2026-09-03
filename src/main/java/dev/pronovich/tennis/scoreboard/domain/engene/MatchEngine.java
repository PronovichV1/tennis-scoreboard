package dev.pronovich.tennis.scoreboard.domain.engene;

import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
import dev.pronovich.tennis.scoreboard.model.Player;

public class MatchEngine implements Engine {

    private final ScoreEngine scoreEngine = new ScoreEngine();
    private final TieBreakEngine tieBreak = new TieBreakEngine();


    @Override
    public void calculation(Player player, MatchScore matchScore) {

        PlayerScore concretePlayerScore = matchScore.getSpecificPlayerScore(player);
        PlayerScore rivalPlayerScore = matchScore.getAnotherPlayerScore(player);


        if (tieBreak.isOn()) {
            tieBreak.calculation(player, matchScore); /// ADD IMPLEMENTATION
        } else {
            scoreEngine.calculation(player, matchScore);
        }
        
        if (!scoreEngine.isGameWon()){
            return;
        }

            concretePlayerScore.addGamesScore(); /// add points

            if (isPlayerWinSet(concretePlayerScore, rivalPlayerScore)){ /// win set
                concretePlayerScore.addSetsScore();
                concretePlayerScore.resetGames();
                rivalPlayerScore.resetGames();
            }

            if (isPlayerWinMatch(player, matchScore)) { /// WINNER
                matchScore.assignTheWinner(player);
            }
        }

    private boolean isPlayerWinSet(PlayerScore concretePlayerScore, PlayerScore rivalPlayerScore) {
        ///
        if (concretePlayerScore.getGames() > 5 &&
        concretePlayerScore.getGames() - rivalPlayerScore.getGames() >= 2){
            return true;
        }
        return false;
    }




    private boolean isPlayerWinMatch(Player player, MatchScore matchScore) {
        return false; /// ADD IMPLEMENTATION
    }
}
