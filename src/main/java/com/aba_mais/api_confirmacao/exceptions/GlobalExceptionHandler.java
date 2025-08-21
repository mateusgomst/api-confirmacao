package com.aba_mais.api_confirmacao.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailJaExisteException.class)
    public ResponseEntity<Map<String, Object>> handleEmailJaExisteException(EmailJaExisteException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Email já existe");
        response.put("message", ex.getMessage());
        response.put("email", ex.getEmail());
        response.put("status", HttpStatus.CONFLICT.value());
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}