package dev.pronovich.tennis.scoreboard.exception;

public class PlayerNotExist extends RuntimeException {
    public PlayerNotExist(String message) {
        super(message);
    }
}
