package com.thbs.lms.exceptionHandler;

public class LearningPlanPathNotFoundException extends RuntimeException {
    public LearningPlanPathNotFoundException(String message) {
        super(message);
    }
}