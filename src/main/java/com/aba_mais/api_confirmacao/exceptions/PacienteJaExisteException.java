package com.aba_mais.api_confirmacao.exceptions;

public class PacienteJaExisteException extends RuntimeException {
    public PacienteJaExisteException(String nome) {
        super("Paciente " + nome + " já existe");
    }
}