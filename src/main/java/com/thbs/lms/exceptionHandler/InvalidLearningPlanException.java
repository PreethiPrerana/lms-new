package com.thbs.lms.exceptionHandler;

public class InvalidLearningPlanException extends RuntimeException {
    public InvalidLearningPlanException(String message) {
        super(message);
    }
}
