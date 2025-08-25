package com.aba_mais.api_confirmacao.dtos;

import com.aba_mais.api_confirmacao.entities.Paciente;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PacienteResponseDto {
    private Long id;
    private String nome;
    private String emailResponsavel;
    private String telefoneResponsavel;

    public PacienteResponseDto(Paciente paciente) {
        this.id = paciente.getId();
        this.nome = paciente.getNome();
        this.emailResponsavel = paciente.getEmailResponsavel();
        this.telefoneResponsavel = paciente.getTelefoneResponsavel();
    }
}