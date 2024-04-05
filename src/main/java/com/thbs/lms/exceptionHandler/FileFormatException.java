package com.thbs.lms.exceptionHandler;

public class FileFormatException extends RuntimeException {
    public FileFormatException(String message) {
        super(message);
    }
}
