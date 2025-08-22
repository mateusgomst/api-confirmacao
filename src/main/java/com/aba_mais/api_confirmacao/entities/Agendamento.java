package com.aba_mais.api_confirmacao.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
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
    
    public Agendamento() {
        this.tokenConfirmacao = UUID.randomUUID().toString();
        this.dataCriacao = LocalDateTime.now();
    }

    public Agendamento(Paciente paciente, LocalDateTime dataHora) {
        this();
        this.paciente = paciente;
        this.dataHora = dataHora;
    }

    public Agendamento(Paciente paciente, LocalDateTime dataHora, StatusAgendamento status) {
        this();
        this.paciente = paciente;
        this.dataHora = dataHora;
        this.status = status;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Paciente getPaciente() {
        return paciente;
    }
    
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    
    public LocalDateTime getDataHora() {
        return dataHora;
    }
    
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
    
    public StatusAgendamento getStatus() {
        return status;
    }
    
    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }
    
    public String getTokenConfirmacao() {
        return tokenConfirmacao;
    }
    
    public void setTokenConfirmacao(String tokenConfirmacao) {
        this.tokenConfirmacao = tokenConfirmacao;
    }
    
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
    
}