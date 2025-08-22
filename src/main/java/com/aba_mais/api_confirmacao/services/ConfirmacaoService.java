package com.aba_mais.api_confirmacao.services;

import com.aba_mais.api_confirmacao.dtos.AgendamentoResponseDto;
import com.aba_mais.api_confirmacao.dtos.ConfirmarAgendamentoDto;
import com.aba_mais.api_confirmacao.dtos.EnvioConfirmacaoResponseDto;
import com.aba_mais.api_confirmacao.entities.StatusAgendamento;
import com.aba_mais.api_confirmacao.exceptions.BusinessException;
import com.aba_mais.api_confirmacao.interfaces.AgendamentoServiceInterface;
import com.aba_mais.api_confirmacao.interfaces.ConfirmacaoServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class ConfirmacaoService implements ConfirmacaoServiceInterface {

    @Autowired
    private AgendamentoServiceInterface agendamentoService;

    @Override
    public EnvioConfirmacaoResponseDto enviarConfirmacao(Long agendamentoId) {
        AgendamentoResponseDto agendamento = agendamentoService.buscarAgendamentoPorId(agendamentoId);

        if (agendamento.getStatus() == StatusAgendamento.CONFIRMADO) {
            throw new BusinessException("Agendamento já foi confirmado, não é necessário reenviar", HttpStatus.CONFLICT);
        }

        if (agendamento.getStatus() == StatusAgendamento.CANCELADO) {
            throw new BusinessException("Não é possível enviar confirmação para agendamento cancelado", HttpStatus.CONFLICT);
        }

        LocalDateTime agora = LocalDateTime.now(ZoneOffset.UTC);
        if (agendamento.getDataHora().isBefore(agora)) {
            throw new BusinessException("Não é possível enviar confirmação para agendamento que já passou", HttpStatus.BAD_REQUEST);
        }

        return new EnvioConfirmacaoResponseDto(agendamento);
    }

    @Override
    public ConfirmarAgendamentoDto confirmarAgendamento(String token) {
        AgendamentoResponseDto agendamentoConfirmado = agendamentoService.confirmarAgendamentoPorToken(token);

        return new ConfirmarAgendamentoDto("Agendamento confirmado com sucesso!", agendamentoConfirmado);
    }


}