package com.thbs.lms.exceptionHandler;

public class InvalidTrainerException extends RuntimeException {
    public InvalidTrainerException(String message) {
        super(message);
    }
}
