package com.hidiscuss.backend.exception;

public class EmptyDiscussionCodeException extends RuntimeException {
    private String message;

    public EmptyDiscussionCodeException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
