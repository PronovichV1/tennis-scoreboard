package dev.pronovich.tennis.scoreboard.domain.engine;

import dev.pronovich.tennis.scoreboard.domain.MatchScore;
import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
import dev.pronovich.tennis.scoreboard.model.Player;

import java.util.Optional;

public class TieBreakEngine implements Engine{

    @Override
    public Optional<Player> calculation(Player player, MatchScore matchScore) {
        PlayerScore concretePlayerScore = matchScore.getSpecificPlayerScore(player);
        PlayerScore rivalPlayerScore = matchScore.getAnotherPlayerScore(player);
        /// TIE-BREAK
        /// if Concrete player has 7 points with range of 2 or more, he wins
        if (concretePlayerScore.getTieBreakPoints() > 6 && isTieBreakPointsDifferenceMoreThenTwo(concretePlayerScore, rivalPlayerScore)){
            matchScore.deactivateTieBreak();
            return Optional.of(player);
        }
        /// increase tieBreakPoints for concretePlayer by 1
        concretePlayerScore.addTieBreakPoints();
        return Optional.empty();
    }

    public boolean isTieBreakPointsDifferenceMoreThenTwo(PlayerScore concretePlayerScore, PlayerScore rivalPlayerScore) {
        return (concretePlayerScore.getTieBreakPoints() - rivalPlayerScore.getTieBreakPoints()) >= 2;
    }
}
