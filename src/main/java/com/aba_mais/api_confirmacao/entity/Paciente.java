package com.aba_mais.api_confirmacao.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pacientes")
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
    
    public Paciente() {}
    
    public Paciente(String nome, String emailResponsavel, String telefoneResponsavel) {
        this.nome = nome;
        this.emailResponsavel = emailResponsavel;
        this.telefoneResponsavel = telefoneResponsavel;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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