package com.sports.league.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidMatchResultException extends RuntimeException {

    public InvalidMatchResultException(String message) {
        super(message);
    }
}
