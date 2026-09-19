package dev.pronovich.tennis.scoreboard.domain.engine;

import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
import dev.pronovich.tennis.scoreboard.model.Player;
import org.springframework.stereotype.Service;

@Service
public class MatchEngine implements Engine {

    private final ScoreEngine scoreEngine = new ScoreEngine();
    private final TieBreakEngine tieBreak = new TieBreakEngine();


    @Override
    public void calculation(Player player, MatchScore matchScore) {

        PlayerScore concretePlayerScore = matchScore.getSpecificPlayerScore(player);
        PlayerScore rivalPlayerScore = matchScore.getAnotherPlayerScore(player);

        if (tieBreak.isOn()) {
            tieBreak.calculation(concretePlayerScore.getPlayer(), matchScore);
        } else {
            scoreEngine.calculation(concretePlayerScore.getPlayer(), matchScore);

        }
        
        if (!scoreEngine.isGameWon() || (tieBreak.isOn() && !tieBreak.isTieBreakWon())){
            return;
        }

            concretePlayerScore.addGamesScore(); /// add points

            if  (concretePlayerScore.getGames() == 6 && rivalPlayerScore.getGames() == 6){ /// tie-break activation
                tieBreak.activateTieBreak(matchScore);
                return;
            }

            if (isPlayerWinSet(concretePlayerScore, rivalPlayerScore)){ /// win set
                concretePlayerScore.addSetsScore();
                concretePlayerScore.resetGames();
                rivalPlayerScore.resetGames();
            }


            if (isPlayerWinMatch(player, matchScore)) { /// WINNER
                matchScore.assignTheWinner(concretePlayerScore.getPlayer());
            }
        }

    /// base win
    private boolean isPlayerWinSet(PlayerScore concretePlayerScore, PlayerScore rivalPlayerScore) {
        if (concretePlayerScore.getGames() == 6 && rivalPlayerScore.getGames() < 5){
            return true;
        }
        if (tieBreak.isTieBreakWon()){
            return true;
        }
        return false;
    }

    /// best of 3
    /// Win a whole game
    public boolean isPlayerWinMatch(Player player, MatchScore matchScore) {
        return ((matchScore.getSpecificPlayerScore(player).getSets() - (matchScore.getAnotherPlayerScore(player).getSets()) >= 2));
    }
}
