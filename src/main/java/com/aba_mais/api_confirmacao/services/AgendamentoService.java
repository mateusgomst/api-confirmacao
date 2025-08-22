package com.aba_mais.api_confirmacao.services;

import com.aba_mais.api_confirmacao.dtos.AgendamentoResponseDto;
import com.aba_mais.api_confirmacao.entities.Agendamento;
import com.aba_mais.api_confirmacao.entities.Paciente;
import com.aba_mais.api_confirmacao.entities.StatusAgendamento;
import com.aba_mais.api_confirmacao.exceptions.BusinessException;
import com.aba_mais.api_confirmacao.interfaces.AgendamentoServiceInterface;
import com.aba_mais.api_confirmacao.repositories.AgendamentoRepository;
import com.aba_mais.api_confirmacao.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoService implements AgendamentoServiceInterface {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public Agendamento criarAgendamento(Long pacienteId, LocalDateTime dataHora) {

        if (pacienteId < 0) {
            throw new BusinessException("PacienteId deve ser um número inteiro positivo.", HttpStatus.BAD_REQUEST);
        }

        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new BusinessException("Paciente não encontrado com ID: " + pacienteId, HttpStatus.NOT_FOUND));

        if (dataHora.isBefore(LocalDateTime.now())) {
            throw new BusinessException("A data do agendamento não pode ser no passado.", HttpStatus.BAD_REQUEST);
        }

        if (agendamentoRepository.existsByPacienteIdAndDataHora(pacienteId, dataHora)) {
            throw new BusinessException("Este paciente já possui um agendamento marcado para esta data e hora.", HttpStatus.CONFLICT);
        }

        int hora = dataHora.getHour();
        if (hora < 8 || hora > 18) {
            throw new BusinessException("Agendamentos só podem ser feitos entre 08:00 e 18:00.", HttpStatus.BAD_REQUEST);
        }

        Agendamento agendamento = new Agendamento(paciente, dataHora);

        return agendamentoRepository.save(agendamento);
    }

    @Override
    public List<AgendamentoResponseDto> listarAgendamentos() {

        List<Agendamento> agendamentos = agendamentoRepository.findAll();

        List<AgendamentoResponseDto> dtos = new ArrayList<>();
        for (Agendamento agendamento : agendamentos) {
            dtos.add(new AgendamentoResponseDto(agendamento));
        }

        return dtos;
    }

    @Override
    public AgendamentoResponseDto buscarAgendamentoPorId(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Agendamento com ID: " + id + " não encontrado",
                        HttpStatus.NOT_FOUND));

        return new AgendamentoResponseDto(agendamento);
    }

    @Override
    public List<AgendamentoResponseDto> listarAgendamentosPorPacienteId(Long id) {
        List<Agendamento> agendamentos = agendamentoRepository.findAllByPacienteId(id);

        if (agendamentos.isEmpty()) {
            throw new BusinessException("Nenhum agendamento encontrado para o paciente com ID: " + id, HttpStatus.NOT_FOUND);
        }

        List<AgendamentoResponseDto> dtos = new ArrayList<>();
        for (Agendamento agendamento : agendamentos) {
            dtos.add(new AgendamentoResponseDto(agendamento));
        }

        return dtos;
    }


    @Override
    public AgendamentoResponseDto confirmarAgendamentoPorToken(String token) {
        Agendamento agendamento = agendamentoRepository.findByTokenConfirmacao(token)
                .orElseThrow(() -> new BusinessException("Token inválido: " + token, HttpStatus.NOT_FOUND));

        if (agendamento.getStatus() == StatusAgendamento.CANCELADO) {
            throw new BusinessException("Não é possível confirmar agendamento cancelado", HttpStatus.CONFLICT);
        }

        if (agendamento.getStatus() == StatusAgendamento.CONFIRMADO) {
            throw new BusinessException("Agendamento já foi confirmado anteriormente", HttpStatus.CONFLICT);
        }

        if (agendamento.getDataHora().isBefore(LocalDateTime.now())) {
            throw new BusinessException("Não é possível confirmar um agendamento que já passou. Data do agendamento: " + agendamento.getDataHora(), HttpStatus.BAD_REQUEST);
        }

        agendamento.setStatus(StatusAgendamento.CONFIRMADO);
        agendamentoRepository.save(agendamento);

        return new AgendamentoResponseDto(agendamento);
    }

    @Override
    public AgendamentoResponseDto cancelarAgendamento(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Agendamento com ID: " + id + " não encontrado", HttpStatus.NOT_FOUND));

        if (agendamento.getStatus() == StatusAgendamento.CANCELADO) {
            throw new BusinessException("Agendamento já foi cancelado", HttpStatus.CONFLICT);
        }

        if (agendamento.getDataHora().isBefore(LocalDateTime.now())) {
            throw new BusinessException("Não é possível cancelar agendamento que já passou. Data do agendamento: " + agendamento.getDataHora(), HttpStatus.BAD_REQUEST);
        }

        agendamento.setStatus(StatusAgendamento.CANCELADO);
        agendamentoRepository.save(agendamento);

        return new AgendamentoResponseDto(agendamento);
    }

}