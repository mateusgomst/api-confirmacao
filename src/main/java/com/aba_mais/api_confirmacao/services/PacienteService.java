package com.aba_mais.api_confirmacao.services;

import java.util.List;

import com.aba_mais.api_confirmacao.dtos.PacienteResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.aba_mais.api_confirmacao.dtos.CriarPacienteRequestDto;
import com.aba_mais.api_confirmacao.entities.Paciente;
import com.aba_mais.api_confirmacao.exceptions.BusinessException;
import com.aba_mais.api_confirmacao.interfaces.PacienteServiceInterface;
import com.aba_mais.api_confirmacao.repositories.PacienteRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PacienteService implements PacienteServiceInterface {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public PacienteResponseDto cadastrarPaciente(CriarPacienteRequestDto pacienteDto) {

        Paciente paciente = new Paciente(
                pacienteDto.getNome(),
                pacienteDto.getEmailResponsavel(),
                pacienteDto.getTelefoneResponsavel()
        );

        PacienteResponseDto pacienteResponseDto = new PacienteResponseDto(pacienteRepository.save(paciente));
        log.info("Paciente cadastrado com sucesso - ID: {}, Nome: {}", pacienteResponseDto.getId(), pacienteResponseDto.getNome());

        return pacienteResponseDto;
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
                .orElseThrow(() -> {
                    return new BusinessException("Paciente não encontrado com o nome: " + nome, HttpStatus.NOT_FOUND);
                });
    }
}