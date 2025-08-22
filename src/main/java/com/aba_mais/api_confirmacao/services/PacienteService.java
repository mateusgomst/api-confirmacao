package com.aba_mais.api_confirmacao.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.aba_mais.api_confirmacao.dtos.CreatePacienteRequestDto;
import com.aba_mais.api_confirmacao.entities.Paciente;
import com.aba_mais.api_confirmacao.exceptions.BusinessException;
import com.aba_mais.api_confirmacao.interfaces.PacienteServiceInterface;
import com.aba_mais.api_confirmacao.repositories.PacienteRepository;

@Service
public class PacienteService implements PacienteServiceInterface {

    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente cadastrarPaciente(CreatePacienteRequestDto pacienteDto) {

        if (pacienteRepository.existsByNome(pacienteDto.getNome())) {
            throw new BusinessException(
                    "Já existe um paciente com este nome: " + pacienteDto.getNome(),
                    HttpStatus.CONFLICT
            );
        }

        Paciente paciente = new Paciente(
                pacienteDto.getNome(),
                pacienteDto.getEmail(),
                pacienteDto.getTelefone()
        );

        return pacienteRepository.save(paciente);
    }

    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

    public Paciente buscarPacientePorNome(String nome) {
        Paciente paciente = pacienteRepository.findByNome(nome);
        if (paciente == null) {
            throw new BusinessException(
                    "Paciente não encontrado com o nome: " + nome,
                    HttpStatus.NOT_FOUND
            );
        }
        return paciente;
    }
}