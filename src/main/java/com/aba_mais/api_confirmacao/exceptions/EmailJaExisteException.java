package com.aba_mais.api_confirmacao.exceptions;

public class EmailJaExisteException extends RuntimeException {

    private String email;

    public EmailJaExisteException(String email) {
        super("Email " + email + " já está cadastrado");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}