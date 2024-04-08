package com.thbs.lms.exceptionHandler;

public class DuplicateLearningPlanPathException extends RuntimeException {
    public DuplicateLearningPlanPathException(String message) {
        super(message);
    }
}