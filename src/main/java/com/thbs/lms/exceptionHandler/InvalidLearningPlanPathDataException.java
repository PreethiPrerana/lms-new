package com.thbs.lms.exceptionHandler;

public class InvalidLearningPlanPathDataException extends RuntimeException {
    public InvalidLearningPlanPathDataException(String message) {
        super(message);
    }
}