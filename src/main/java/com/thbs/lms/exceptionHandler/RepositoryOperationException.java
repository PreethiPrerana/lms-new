package com.thbs.lms.exceptionHandler;

public class RepositoryOperationException extends RuntimeException {
    public RepositoryOperationException(String message) {
        super(message);
    }
}
