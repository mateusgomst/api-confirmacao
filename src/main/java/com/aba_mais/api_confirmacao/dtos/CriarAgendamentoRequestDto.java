package com.aba_mais.api_confirmacao.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CriarAgendamentoRequestDto {

    @NotNull(message = "PacienteId é obrigatório")
    private Long pacienteId;

    @NotNull(message = "Data e hora são obrigatórias")
    private LocalDateTime dataHora;

}
