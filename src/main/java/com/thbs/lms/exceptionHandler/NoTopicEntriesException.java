package com.thbs.lms.exceptionHandler;

public class NoTopicEntriesException extends RuntimeException {
    public NoTopicEntriesException(String message) {
        super(message);
    }
}
