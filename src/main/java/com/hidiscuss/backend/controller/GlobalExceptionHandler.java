package com.hidiscuss.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class) // Controller @Valid processing
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException(MethodArgumentNotValidException e) {
        logger.warn(e.getMessage());
        return e.getBindingResult().getFieldError().getDefaultMessage(); // TODO : return proper error message
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException(IllegalArgumentException e) {
        logger.warn(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException(MissingServletRequestParameterException e) {
        logger.warn(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e) {
        logger.error(e.getMessage());
        return e.getMessage();
    }
}
