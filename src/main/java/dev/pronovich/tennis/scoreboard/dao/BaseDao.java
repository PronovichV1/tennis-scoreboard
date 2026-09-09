package dev.pronovich.tennis.scoreboard.dao;

import java.util.Optional;

public interface BaseDao<T> {
    Optional<T> save(T Match);
}
