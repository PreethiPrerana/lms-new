package com.thbs.lms.exceptionHandler;

public class UnexpectedErrorException extends RuntimeException {
    public UnexpectedErrorException(String message) {
        super(message);
    }
}
