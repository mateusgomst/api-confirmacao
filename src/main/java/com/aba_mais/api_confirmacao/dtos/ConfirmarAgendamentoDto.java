package com.aba_mais.api_confirmacao.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConfirmarAgendamentoDto {
    private String message;
    private AgendamentoResponseDto agendamento;

    public ConfirmarAgendamentoDto(String message, AgendamentoResponseDto agendamento) {
        this.message = message;
        this.agendamento = agendamento;
    }

}