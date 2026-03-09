package com.karthik.orderservice.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<?> handleOrderException(OrderException ex) {
        return ResponseEntity.badRequest()
                .body(Map.of(
                        "error", ex.getMessage(),
                        "time", LocalDateTime.now()
                ));
    }
}