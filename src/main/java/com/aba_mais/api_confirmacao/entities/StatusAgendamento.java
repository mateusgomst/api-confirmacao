package com.aba_mais.api_confirmacao.entities;

public enum StatusAgendamento {
    PENDENTE("PENDENTE"),
    CONFIRMADO("CONFIRMADO"),
    CANCELADO("CANCELADO");
    
    private final String descricao;
    
    StatusAgendamento(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}