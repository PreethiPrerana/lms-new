package com.thbs.lms.exceptionHandler;

public class InvalidTopicDataException extends RuntimeException {
    public InvalidTopicDataException(String message) {
        super(message);
    }
}
