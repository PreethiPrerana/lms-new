package com.thbs.lms.exceptionHandler;

public class InvalidLevelException extends RuntimeException {
    public InvalidLevelException(String message) {
        super(message);
    }
}
