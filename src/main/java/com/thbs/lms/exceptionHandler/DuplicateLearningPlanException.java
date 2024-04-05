package com.thbs.lms.exceptionHandler;

public class DuplicateLearningPlanException extends RuntimeException {
    public DuplicateLearningPlanException(String message) {
        super(message);
    }
}