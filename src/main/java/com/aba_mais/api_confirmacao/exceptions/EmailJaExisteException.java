package com.aba_mais.api_confirmacao.exceptions;

public class EmailJaExisteException extends RuntimeException {
    private final String email;

    public EmailJaExisteException(String email) {
        super("Email já existe: " + email);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}