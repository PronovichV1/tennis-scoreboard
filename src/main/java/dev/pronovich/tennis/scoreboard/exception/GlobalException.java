package dev.pronovich.tennis.scoreboard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException{

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalArgumentException(IllegalArgumentException ex){
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return response;
    }

    @ExceptionHandler(MatchNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleMatchNotExistException(MatchNotExistException ex){
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return response;
    }


    @ExceptionHandler(DatabaseException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleDatabaseException(DatabaseException ex){
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return response;
    }
}
