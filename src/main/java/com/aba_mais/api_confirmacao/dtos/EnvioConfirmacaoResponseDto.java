package com.aba_mais.api_confirmacao.dtos;

import java.time.format.DateTimeFormatter;

public class EnvioConfirmacaoResponseDto {
    private String message;
    private String canal;
    private String destinatario;
    private String linkConfirmacao;
    private String conteudoMensagem;

    public EnvioConfirmacaoResponseDto(AgendamentoResponseDto agendamento) {
        this.message = "Mensagem de confirmação enviada";
        this.canal = "EMAIL";
        this.destinatario = agendamento.getPacienteNome();
        this.linkConfirmacao = "http://localhost:8080/api/confirmacao/" + agendamento.getTokenConfirmacao();

        String dataFormatada = agendamento.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM"));
        String horaFormatada = agendamento.getDataHora().format(DateTimeFormatter.ofPattern("HH:mm"));

        this.conteudoMensagem = String.format(
                "Olá! Confirme o agendamento da sessão do %s para %s às %s. Clique: %s",
                agendamento.getPacienteNome(),
                dataFormatada,
                horaFormatada,
                this.linkConfirmacao
        );
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getLinkConfirmacao() {
        return linkConfirmacao;
    }

    public void setLinkConfirmacao(String linkConfirmacao) {
        this.linkConfirmacao = linkConfirmacao;
    }

    public String getConteudoMensagem() {
        return conteudoMensagem;
    }

    public void setConteudoMensagem(String conteudoMensagem) {
        this.conteudoMensagem = conteudoMensagem;
    }
}