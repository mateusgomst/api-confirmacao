package com.aba_mais.api_confirmacao.exceptions;

public class EmailNullException extends RuntimeException {
    public EmailNullException(String email) {
        super("Email não pode ser nulo ou vazio");
    }
}