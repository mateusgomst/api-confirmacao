package com.aba_mais.api_confirmacao.services;

import java.util.List;

import com.aba_mais.api_confirmacao.dtos.PacienteResponseDto;
import com.aba_mais.api_confirmacao.dtos.CriarPacienteRequestDto;
import com.aba_mais.api_confirmacao.dtos.AtualizarPacienteRequestDto;
import com.aba_mais.api_confirmacao.dtos.RespostaOperacaoPacienteDto;
import com.aba_mais.api_confirmacao.entities.Agendamento;
import com.aba_mais.api_confirmacao.entities.Paciente;
import com.aba_mais.api_confirmacao.entities.StatusAgendamento;
import com.aba_mais.api_confirmacao.exceptions.BusinessException;
import com.aba_mais.api_confirmacao.interfaces.PacienteServiceInterface;
import com.aba_mais.api_confirmacao.repositories.AgendamentoRepository;
import com.aba_mais.api_confirmacao.repositories.PacienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PacienteService implements PacienteServiceInterface {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Override
    public PacienteResponseDto cadastrarPaciente(CriarPacienteRequestDto pacienteDto) {

        Paciente existente = pacienteRepository
                .findByNomeAndEmailResponsavelAndTelefoneResponsavel(
                        pacienteDto.getNome(),
                        pacienteDto.getEmailResponsavel(),
                        pacienteDto.getTelefoneResponsavel()
                ).orElse(null);

        if (existente != null) {
            log.info("Paciente já existente retornado - ID: {}, Nome: {}", existente.getId(), existente.getNome());
            return new PacienteResponseDto(existente);
        }

        Paciente paciente = new Paciente(pacienteDto.getNome(), pacienteDto.getEmailResponsavel(), pacienteDto.getTelefoneResponsavel());
        paciente.setAtivo(true);

        Paciente salvo = pacienteRepository.save(paciente);
        log.info("Paciente cadastrado com sucesso - ID: {}, Nome: {}", salvo.getId(), salvo.getNome());

        return new PacienteResponseDto(salvo);
    }

    @Override
    public List<PacienteResponseDto> listarPacientes() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        log.info("Retornando {} pacientes", pacientes.size());
        return pacientes.stream().map(PacienteResponseDto::new).toList();
    }

    @Override
    public PacienteResponseDto buscarPacientePorNome(String nome) {
        Paciente paciente = pacienteRepository.findByNome(nome)
                .orElseThrow(() -> new BusinessException("Paciente não encontrado com o nome: " + nome, HttpStatus.NOT_FOUND));
        return new PacienteResponseDto(paciente);
    }

    @Override
    public PacienteResponseDto atualizarPaciente(Long id, AtualizarPacienteRequestDto pacienteDto) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Paciente com ID: " + id + " não encontrado", HttpStatus.NOT_FOUND));

        paciente.setNome(pacienteDto.getNome());
        paciente.setEmailResponsavel(pacienteDto.getEmailResponsavel());
        paciente.setTelefoneResponsavel(pacienteDto.getTelefoneResponsavel());

        Paciente atualizado = pacienteRepository.save(paciente);
        log.info("Paciente atualizado com sucesso - ID: {}, Nome: {}", atualizado.getId(), atualizado.getNome());

        return new PacienteResponseDto(atualizado);
    }

    @Override
    public RespostaOperacaoPacienteDto deletarPaciente(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Paciente com ID: " + id + " não encontrado", HttpStatus.NOT_FOUND));

        paciente.setAtivo(false);
        pacienteRepository.save(paciente);

        List<Agendamento> agendamentos = agendamentoRepository.findAllByPacienteId(paciente.getId());
        for (Agendamento ag : agendamentos) {
            ag.setStatus(StatusAgendamento.CANCELADO);
        }
        agendamentoRepository.saveAll(agendamentos);

        log.info("Paciente desativado e agendamentos cancelados - ID: {}, Nome: {}", paciente.getId(), paciente.getNome());

        return new RespostaOperacaoPacienteDto("Paciente desativado e suas consultas foram canceladas com sucesso", new PacienteResponseDto(paciente)
        );
    }
}
