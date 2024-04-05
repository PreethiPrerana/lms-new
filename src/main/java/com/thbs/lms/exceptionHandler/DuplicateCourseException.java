package com.thbs.lms.exceptionHandler;

public class DuplicateCourseException extends RuntimeException {
    public DuplicateCourseException(String message) {
        super(message);
    }
}
