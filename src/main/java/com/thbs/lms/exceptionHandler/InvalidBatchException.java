package com.thbs.lms.exceptionHandler;

public class InvalidBatchException extends RuntimeException {
    public InvalidBatchException(String message) {
        super(message);
    }
}
