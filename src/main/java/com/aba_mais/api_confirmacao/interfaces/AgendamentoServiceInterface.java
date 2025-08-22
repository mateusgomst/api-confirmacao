package com.aba_mais.api_confirmacao.interfaces;

import com.aba_mais.api_confirmacao.dtos.AgendamentoResponseDto;
import com.aba_mais.api_confirmacao.entities.Agendamento;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoServiceInterface {
    Agendamento criarAgendamento(Long pacienteId, LocalDateTime dataHora);
    AgendamentoResponseDto buscarAgendamentoPorId(Long id);
    List<AgendamentoResponseDto> listarAgendamentos();
    List<AgendamentoResponseDto> listarAgendamentosPorPacienteId(Long id);
    AgendamentoResponseDto confirmarAgendamentoPorToken(String token);
    AgendamentoResponseDto cancelarAgendamento(Long id);
}