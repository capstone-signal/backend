package com.hidiscuss.backend.exception;

public class UserAuthorityException extends RuntimeException {
    private String message;

    public UserAuthorityException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
