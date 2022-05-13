package com.hidiscuss.backend.exception;

public class LiveReviewTimeConvertException extends RuntimeException {
    private String message;

    public LiveReviewTimeConvertException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
