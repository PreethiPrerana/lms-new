package com.thbs.lms.exceptionHandler;

public class LearningPlanNotFoundException extends RuntimeException {
    public LearningPlanNotFoundException(String message) {
        super(message);
    }
}