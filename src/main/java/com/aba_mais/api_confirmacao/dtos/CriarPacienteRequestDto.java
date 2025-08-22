package com.aba_mais.api_confirmacao.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CriarPacienteRequestDto {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter um formato válido")
    private String emailResponsavel;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(
            regexp = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$",
            message = "Telefone deve estar no formato (99) 99999-9999 ou (99) 9999-9999"
    )
    private String telefoneResponsavel;

    public CriarPacienteRequestDto() {
    }

    public CriarPacienteRequestDto(String nome, String emailResponsavel, String telefoneResponsavel) {
        this.nome = nome;
        this.emailResponsavel = emailResponsavel;
        this.telefoneResponsavel = telefoneResponsavel;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmailResponsavel() {
        return emailResponsavel;
    }

    public void setEmailResponsavel(String emailResponsavel) {
        this.emailResponsavel = emailResponsavel;
    }

    public String getTelefoneResponsavel() {
        return telefoneResponsavel;
    }

    public void setTelefoneResponsavel(String telefoneResponsavel) {
        this.telefoneResponsavel = telefoneResponsavel;
    }
}