package com.thbs.lms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FileProcessingException.class)
    public ResponseEntity<String> handleFileProcessingException(FileProcessingException ex) {
      String errorMessage = ex.getMessage(); 
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(InvalidSheetFormatException.class)
    public ResponseEntity<String> handleInvalidSheetFormatException(InvalidSheetFormatException ex) {
        String errorMessage = ex.getMessage(); 
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

}

