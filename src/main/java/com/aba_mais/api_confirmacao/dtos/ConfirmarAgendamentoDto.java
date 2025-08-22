package com.aba_mais.api_confirmacao.dtos;

public class ConfirmarAgendamentoDto {
    private String message;
    private AgendamentoResponseDto agendamento;

    public ConfirmarAgendamentoDto() {}

    public ConfirmarAgendamentoDto(String message, AgendamentoResponseDto agendamento) {
        this.message = message;
        this.agendamento = agendamento;
    }

    // Getters e Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AgendamentoResponseDto getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(AgendamentoResponseDto agendamento) {
        this.agendamento = agendamento;
    }
}