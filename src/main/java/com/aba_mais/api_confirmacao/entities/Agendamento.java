package com.aba_mais.api_confirmacao.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "agendamentos")
public class Agendamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;
    
    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private StatusAgendamento status = StatusAgendamento.PENDENTE;
    
    @Column(name = "token_confirmacao", nullable = false, unique = true, length = 255)
    private String tokenConfirmacao;
    
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    public Agendamento(Paciente paciente, LocalDateTime dataHora) {
        this();
        this.paciente = paciente;
        this.dataHora = dataHora;
        this.tokenConfirmacao = UUID.randomUUID().toString();
        this.dataCriacao = LocalDateTime.now();
    }

}