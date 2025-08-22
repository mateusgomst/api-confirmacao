package com.aba_mais.api_confirmacao.services;

import com.aba_mais.api_confirmacao.dtos.AgendamentoResponseDto;
import com.aba_mais.api_confirmacao.dtos.EnvioConfirmacaoResponseDto;
import com.aba_mais.api_confirmacao.entities.Agendamento;
import com.aba_mais.api_confirmacao.interfaces.AgendamentoServiceInterface;
import com.aba_mais.api_confirmacao.interfaces.ConfirmacaoServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmacaoService implements ConfirmacaoServiceInterface {

    @Autowired
    private AgendamentoServiceInterface agendamentoService;

    @Override
    public EnvioConfirmacaoResponseDto enviarConfirmacao(Long agendamentoId) {
        AgendamentoResponseDto agendamento = agendamentoService.buscarAgendamentoPorId(agendamentoId);

        //Aqui seria o envio real do email..

        return new EnvioConfirmacaoResponseDto(agendamento);
    }
}
