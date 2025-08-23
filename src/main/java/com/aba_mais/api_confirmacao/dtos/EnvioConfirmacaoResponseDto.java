package com.aba_mais.api_confirmacao.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
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
}