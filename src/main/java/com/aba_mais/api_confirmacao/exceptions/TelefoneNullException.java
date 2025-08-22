package com.aba_mais.api_confirmacao.exceptions;

public class TelefoneNullException extends RuntimeException {
    public TelefoneNullException(String telefone) {
        super("Telefone do paciente não pode ser nulo ou vazio");
    }
}