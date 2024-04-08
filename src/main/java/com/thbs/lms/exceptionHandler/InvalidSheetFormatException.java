package com.thbs.lms.exceptionHandler;

public class InvalidSheetFormatException extends RuntimeException {
    public InvalidSheetFormatException(String message) {
        super(message);
    }
}
