package com.razal.caback.exception;

import com.razal.caback.exception.custom.BinanceApiException;
import com.razal.caback.exception.custom.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@Slf4j
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<?> handleResourceNotFound(NotFoundException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.ok(new ExceptionResponse(404, NOT_FOUND, ex.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<?> handleResourceNotFound(BinanceApiException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.ok(new ExceptionResponse(500, SERVICE_UNAVAILABLE , ex.getMessage()));
    }
}
