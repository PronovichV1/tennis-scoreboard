package dev.pronovich.tennis.scoreboard.exception;

public class MatchNotExistException extends RuntimeException {
    public MatchNotExistException(String message) {
        super(message);
    }
}
