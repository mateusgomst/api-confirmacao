package com.aba_mais.api_confirmacao.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(PacienteJaExisteException.class)
    public ResponseEntity<String> handlePacienteJaExiste(PacienteJaExisteException ex) {
        logger.warn("Paciente ja existe: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    @ExceptionHandler(PacienteNaoExisteException.class)
    public ResponseEntity<String> handlePacienteNaoExiste(PacienteNaoExisteException ex) {
        logger.warn("Paciente nao existe: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(NomeNullException.class)
    public ResponseEntity<String> handleNomeNull(NomeNullException ex) {
        logger.warn("Nome null: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(TelefoneNullException.class)
    public ResponseEntity<String> handleTelefoneNull(TelefoneNullException ex) {
        logger.warn("Telefone null: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(EmailNullException.class)
    public ResponseEntity<String> handleEmailNull(EmailNullException ex) {
        logger.warn("Email null: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        logger.error("Erro inesperado: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno do servidor");
    }
}