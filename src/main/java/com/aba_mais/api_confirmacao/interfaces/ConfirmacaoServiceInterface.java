package com.aba_mais.api_confirmacao.interfaces;

import com.aba_mais.api_confirmacao.dtos.ConfirmarAgendamentoDto;
import com.aba_mais.api_confirmacao.dtos.EnvioConfirmacaoResponseDto;

public interface ConfirmacaoServiceInterface {
    EnvioConfirmacaoResponseDto enviarConfirmacao(Long agendamentoId);
    ConfirmarAgendamentoDto confirmarAgendamento(String token);
}
