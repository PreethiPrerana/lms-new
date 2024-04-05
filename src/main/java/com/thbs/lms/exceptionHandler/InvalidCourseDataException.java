package com.thbs.lms.exceptionHandler;

public class InvalidCourseDataException extends RuntimeException {
    public InvalidCourseDataException(String message) {
        super(message);
    }
}