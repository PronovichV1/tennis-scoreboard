package dev.pronovich.tennis.scoreboard;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TieBreakTest {


    /// concretePTBP = 1,2,3,4,5,6 - rivalPTBP = 0,1,2,3,4,5,6

    ///  concretePTBP = 7 - rivalPTBP = 0,1,2,3,4,5  concretePWinTB

    /// concretePTBP = 7,8 - rivalPTBP = 8  concretePWinTB tie

    /// concretePTBP = 10 - rivalPTBP = 8  concretePWinTB winATie

    /// error player does not exist in this match
}
