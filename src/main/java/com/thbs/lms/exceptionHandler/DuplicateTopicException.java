package com.thbs.lms.exceptionHandler;

public class DuplicateTopicException extends RuntimeException {
    public DuplicateTopicException(String message) {
        super(message);
    }
}
