// src/main/java/com/aba_mais/api_confirmacao/dtos/AgendamentoResponseDto.java

package com.aba_mais.api_confirmacao.dtos;

import com.aba_mais.api_confirmacao.entities.Agendamento;
import com.aba_mais.api_confirmacao.entities.StatusAgendamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AgendamentoResponseDto {
    private Long id;
    private String pacienteNome;
    private LocalDateTime dataHora;
    private StatusAgendamento status;
    private String tokenConfirmacao;

    public AgendamentoResponseDto(Agendamento agendamento) {
        this.id = agendamento.getId();
        this.pacienteNome = agendamento.getPaciente().getNome();
        this.dataHora = agendamento.getDataHora();
        this.status = agendamento.getStatus();
        this.tokenConfirmacao = agendamento.getTokenConfirmacao();
    }

}