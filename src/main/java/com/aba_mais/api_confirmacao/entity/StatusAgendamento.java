package com.aba_mais.api_confirmacao.entity;

public enum StatusAgendamento {
    PENDENTE("PENDENTE"),
    CONFIRMADO("CONFIRMADO"),
    CANCELADO("CANCELADO");
    
    private final String descricao;
    
    StatusAgendamento(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    @Override
    public String toString() {
        return this.descricao;
    }
}