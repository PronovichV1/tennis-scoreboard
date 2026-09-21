package dev.pronovich.tennis.scoreboard.validator;

import dev.pronovich.tennis.scoreboard.dto.request.GenerateMatchDto;
import org.springframework.stereotype.Component;

@Component
public class MatchNamesValidation implements Validator<GenerateMatchDto>{

    @Override
    public void validate(GenerateMatchDto generateMatchDto) {
        if (generateMatchDto.firstPlayerName().equals(generateMatchDto.secondPlayerName())){
            throw new IllegalArgumentException("Players names cannot be the same");
        }
    }
}
