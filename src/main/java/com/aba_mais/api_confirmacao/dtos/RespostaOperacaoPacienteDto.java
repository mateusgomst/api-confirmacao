package com.aba_mais.api_confirmacao.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RespostaOperacaoPacienteDto {
    private String mensagem;
    private PacienteResponseDto paciente;
}
