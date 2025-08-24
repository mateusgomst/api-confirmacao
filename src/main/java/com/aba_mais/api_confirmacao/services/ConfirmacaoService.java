package com.aba_mais.api_confirmacao.services;

import com.aba_mais.api_confirmacao.dtos.AgendamentoResponseDto;
import com.aba_mais.api_confirmacao.dtos.ConfirmarAgendamentoDto;
import com.aba_mais.api_confirmacao.dtos.EnvioConfirmacaoResponseDto;
import com.aba_mais.api_confirmacao.entities.StatusAgendamento;
import com.aba_mais.api_confirmacao.exceptions.BusinessException;
import com.aba_mais.api_confirmacao.interfaces.AgendamentoServiceInterface;
import com.aba_mais.api_confirmacao.interfaces.ConfirmacaoServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Service
public class ConfirmacaoService implements ConfirmacaoServiceInterface {

    @Autowired
    private AgendamentoServiceInterface agendamentoService;

    @Override
    public EnvioConfirmacaoResponseDto enviarConfirmacao(Long agendamentoId) {
        AgendamentoResponseDto agendamento = agendamentoService.buscarAgendamentoPorId(agendamentoId);

        if (agendamento.getStatus() == StatusAgendamento.CONFIRMADO) {
            log.info("Agendamento já confirmado, não é necessário reenviar - ID: {}", agendamentoId);
            return new EnvioConfirmacaoResponseDto(agendamento, "Mensagem de confirmação já foi enviada!!!");
        }

        if (agendamento.getStatus() == StatusAgendamento.CANCELADO) {
            throw new BusinessException("Não é possível enviar confirmação para agendamento cancelado", HttpStatus.CONFLICT);
        }//tmb poderiamos apenas reenviar aq e não lançar uma exceção, pq vai enviar a "mensagem" porem n tem como confirmar a consulta ja estando cancelado

        LocalDateTime agora = LocalDateTime.now(ZoneOffset.UTC);
        if (agendamento.getDataHora().isBefore(agora)) {
            throw new BusinessException("Não é possível enviar confirmação para agendamento que já passou", HttpStatus.BAD_REQUEST);
        }

        log.info("Confirmação enviada com sucesso - ID: {}", agendamentoId);
        return new EnvioConfirmacaoResponseDto(agendamento);
    }


    @Override
    public ConfirmarAgendamentoDto confirmarAgendamento(String token) {
        AgendamentoResponseDto agendamentoConfirmado = agendamentoService.confirmarAgendamentoPorToken(token);
        log.info("Agendamento confirmado via token - ID: {}", agendamentoConfirmado.getId());

        return new ConfirmarAgendamentoDto("Agendamento confirmado com sucesso!", agendamentoConfirmado);
    }


}