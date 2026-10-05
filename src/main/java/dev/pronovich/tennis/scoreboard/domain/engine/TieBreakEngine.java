package dev.pronovich.tennis.scoreboard.domain.engine;

import dev.pronovich.tennis.scoreboard.domain.TennisMatch;
import dev.pronovich.tennis.scoreboard.domain.PlayerScore;
import dev.pronovich.tennis.scoreboard.model.Player;

import java.util.Optional;

public class TieBreakEngine implements Engine{

    @Override
    public Optional<Player> calculation(Player player, TennisMatch tennisMatch) {
        PlayerScore concretePlayerScore = tennisMatch.getSpecificPlayerScore(player);
        PlayerScore rivalPlayerScore = tennisMatch.getAnotherPlayerScore(player);
        concretePlayerScore.addTieBreakPoints();
        /// TIE-BREAK
        /// increase tieBreakPoints for concretePlayer by 1
        /// if Concrete player has 7 points with range of 2 or more, he wins
        if (concretePlayerScore.getTieBreakPoints() >= 7 && isTieBreakPointsDifferenceMoreThenTwo(concretePlayerScore, rivalPlayerScore)){
            tennisMatch.deactivateTieBreak();
            return Optional.of(concretePlayerScore.getPlayer());
        }
        return Optional.empty();
    }

    private boolean isTieBreakPointsDifferenceMoreThenTwo(PlayerScore concretePlayerScore, PlayerScore rivalPlayerScore) {
        return (concretePlayerScore.getTieBreakPoints() - rivalPlayerScore.getTieBreakPoints()) >= 2;
    }
}
