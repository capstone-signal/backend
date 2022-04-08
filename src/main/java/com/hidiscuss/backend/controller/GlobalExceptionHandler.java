package com.hidiscuss.backend.controller;

import com.hidiscuss.backend.exception.GithubException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class) // Controller @Valid processing
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException(MethodArgumentNotValidException e) {
        logger.warn(e.getMessage());
        ObjectError error = e.getBindingResult().getAllErrors().get(0);
        return error.getDefaultMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException(IllegalArgumentException e) {
        logger.warn(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(GithubException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException(GithubException e) {
        logger.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e) {
        logger.error(e.getMessage());
        return e.getMessage();
    }
}
