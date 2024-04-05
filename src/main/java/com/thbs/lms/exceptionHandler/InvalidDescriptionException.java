package com.thbs.lms.exceptionHandler;

public class InvalidDescriptionException extends RuntimeException {
    public InvalidDescriptionException(String message) {
        super(message);
    }
}
