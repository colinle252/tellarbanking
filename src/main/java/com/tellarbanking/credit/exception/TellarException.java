package com.tellarbanking.credit.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TellarException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericException> handleException(Exception ex) {
        return ResponseEntity.ok(GenericException.builder()
                        .status("FAILED")
                        .message(ex.getMessage()).build());
    }
}
