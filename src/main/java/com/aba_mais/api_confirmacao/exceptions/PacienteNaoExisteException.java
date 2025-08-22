package com.aba_mais.api_confirmacao.exceptions;

public class PacienteNaoExisteException extends RuntimeException {
    public PacienteNaoExisteException(String nome) {
        super("Paciente " + nome + " não existe");
    }
}