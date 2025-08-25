package com.aba_mais.api_confirmacao.services;

import java.util.List;

import com.aba_mais.api_confirmacao.dtos.PacienteResponseDto;
import com.aba_mais.api_confirmacao.dtos.CriarPacienteRequestDto;
import com.aba_mais.api_confirmacao.dtos.AtualizarPacienteRequestDto;
import com.aba_mais.api_confirmacao.entities.Paciente;
import com.aba_mais.api_confirmacao.exceptions.BusinessException;
import com.aba_mais.api_confirmacao.interfaces.PacienteServiceInterface;
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

        Paciente salvo = pacienteRepository.save(paciente);
        log.info("Paciente cadastrado com sucesso - ID: {}, Nome: {}", salvo.getId(), salvo.getNome());

        return new PacienteResponseDto(salvo);
    }

    @Override
    public List<Paciente> listarPacientes() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        log.info("Retornando {} pacientes", pacientes.size());
        return pacientes;
    }

    @Override
    public Paciente buscarPacientePorNome(String nome) {
        return pacienteRepository.findByNome(nome)
                .orElseThrow(() -> new BusinessException("Paciente não encontrado com o nome: " + nome, HttpStatus.NOT_FOUND));
    }

    @Override
    public PacienteResponseDto atualizarPaciente(Long id, AtualizarPacienteRequestDto pacienteDto) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        "Paciente com ID: " + id + " não encontrado",
                        HttpStatus.NOT_FOUND
                ));

        paciente.setNome(pacienteDto.getNome());
        paciente.setEmailResponsavel(pacienteDto.getEmailResponsavel());
        paciente.setTelefoneResponsavel(pacienteDto.getTelefoneResponsavel());

        Paciente atualizado = pacienteRepository.save(paciente);
        log.info("Paciente atualizado com sucesso - ID: {}, Nome: {}", atualizado.getId(), atualizado.getNome());

        return new PacienteResponseDto(atualizado);
    }

    @Override
    public void deletarPaciente(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Paciente com ID: " + id + " não encontrado", HttpStatus.NOT_FOUND));

        pacienteRepository.delete(paciente);
        log.info("Paciente deletado com sucesso - ID: {}, Nome: {}", paciente.getId(), paciente.getNome());
    }
}
