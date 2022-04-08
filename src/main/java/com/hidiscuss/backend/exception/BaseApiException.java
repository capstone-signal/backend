package com.hidiscuss.backend.exception;

public class BaseApiException extends Exception {
    private String message;

    public BaseApiException(String message) {
        this.message = message;
    }
}