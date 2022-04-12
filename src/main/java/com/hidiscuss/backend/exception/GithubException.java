package com.hidiscuss.backend.exception;

public class GithubException extends RuntimeException {
    private String message;

    public GithubException(String message) {
        super(message);
        this.message = message;
    }

    public GithubException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
