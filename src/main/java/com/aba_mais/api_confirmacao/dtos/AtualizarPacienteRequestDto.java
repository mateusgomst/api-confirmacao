package com.aba_mais.api_confirmacao.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AtualizarPacienteRequestDto {

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
}
