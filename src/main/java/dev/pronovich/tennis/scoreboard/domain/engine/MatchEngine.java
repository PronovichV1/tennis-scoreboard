package dev.pronovich.tennis.scoreboard.domain.engine;

import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
import dev.pronovich.tennis.scoreboard.model.Player;

public class MatchEngine implements Engine {

    private final ScoreEngine scoreEngine;
    private final TieBreakEngine tieBreak ;

    public MatchEngine(){
        this.scoreEngine = new ScoreEngine();
        this.tieBreak = new TieBreakEngine();

    }


    @Override
    public void calculation(Player player, MatchScore matchScore) {

        PlayerScore concretePlayerScore = matchScore.getSpecificPlayerScore(player);
        PlayerScore rivalPlayerScore = matchScore.getAnotherPlayerScore(player);

        if (matchScore.isTieBreak()) {
            tieBreak.calculation(concretePlayerScore.getPlayer(), matchScore);
        } else {
            scoreEngine.calculation(concretePlayerScore.getPlayer(), matchScore);
        }

        /// bad ddd design if  !matchScore().gameIsWon() || (matchScore.isOn && !matchScore.isTieBreakIsWon())
        if (!matchScore.isGameWon() || (matchScore.isTieBreak() && !matchScore.isTieBreakWon())){
            return;
        }

            concretePlayerScore.addGamesScore(); /// add points

            if  (concretePlayerScore.getGames() == 6 && rivalPlayerScore.getGames() == 6){ /// tie-break activation
                matchScore.activateTieBreak();
                return;
            }

            if (isPlayerWinSet(matchScore, concretePlayerScore)){ /// win set
                concretePlayerScore.addSetsScore();
                concretePlayerScore.resetGames();
                rivalPlayerScore.resetGames();
                matchScore.setTieBreakWon(false);
            }


            if (isPlayerWinMatch(player, matchScore)) { /// WINNER
                matchScore.assignTheWinner(concretePlayerScore.getPlayer());
            }
        }

    /// base win
    private boolean isPlayerWinSet(MatchScore matchScore, PlayerScore concretePLayerScore) {
        if (concretePLayerScore.getGames() == 6 && matchScore.getAnotherPlayerScore(concretePLayerScore.getPlayer()).getGames() < 5){
            return true;
        }
        if (matchScore.isTieBreakWon()){
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
