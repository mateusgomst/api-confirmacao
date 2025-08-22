package com.aba_mais.api_confirmacao.dtos;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CriarAgendamentoRequestDto {

    @NotNull(message = "PacienteId é obrigatório")
    private Long pacienteId;

    @NotNull(message = "Data e hora são obrigatórias")
    private LocalDateTime dataHora;

    public CriarAgendamentoRequestDto() {
    }

    public CriarAgendamentoRequestDto(Long pacienteId, LocalDateTime dataHora) {
        this.pacienteId = pacienteId;
        this.dataHora = dataHora;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
