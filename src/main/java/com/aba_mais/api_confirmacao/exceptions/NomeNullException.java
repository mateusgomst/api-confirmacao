package com.aba_mais.api_confirmacao.exceptions;

public class NomeNullException extends RuntimeException {
    public NomeNullException(String nome) {
        super("Nome do paciente não pode ser nulo");
    }
}
