package com.aba_mais.api_confirmacao.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pacientes")
@NoArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "email_responsavel", nullable = false, length = 255)
    private String emailResponsavel;

    @Column(name = "telefone_responsavel", nullable = false, length = 20)
    private String telefoneResponsavel;

    @Column(name = "ativo", nullable = false)
    private boolean ativo = true;

    public Paciente(String nome, String emailResponsavel, String telefoneResponsavel) {
        this.nome = nome;
        this.emailResponsavel = (emailResponsavel == null || emailResponsavel.isEmpty()) ? null : emailResponsavel;
        this.telefoneResponsavel = telefoneResponsavel;
        this.ativo = true;
    }

    public boolean getAtivo(){
        return this.ativo;
    }
}
