package com.aba_mais.api_confirmacao.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.aba_mais.api_confirmacao.dtos.CriarPacienteRequestDto;
import com.aba_mais.api_confirmacao.entities.Paciente;
import com.aba_mais.api_confirmacao.exceptions.BusinessException;
import com.aba_mais.api_confirmacao.interfaces.PacienteServiceInterface;
import com.aba_mais.api_confirmacao.repositories.PacienteRepository;

@Service
public class PacienteService implements PacienteServiceInterface {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public Paciente cadastrarPaciente(CriarPacienteRequestDto pacienteDto) {

        if (pacienteRepository.existsByNome(pacienteDto.getNome())) {
            throw new BusinessException(
                    "Já existe um paciente com este nome: " + pacienteDto.getNome(),
                    HttpStatus.CONFLICT
            );
        }

        Paciente paciente = new Paciente(
                pacienteDto.getNome(),
                pacienteDto.getEmailResponsavel(),
                pacienteDto.getTelefoneResponsavel()
        );

        return pacienteRepository.save(paciente);
    }

    @Override
    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

    @Override
    public Paciente buscarPacientePorNome(String nome) {
        return pacienteRepository.findByNome(nome)
                .orElseThrow(() -> new BusinessException(
                        "Paciente não encontrado com o nome: " + nome,
                        HttpStatus.NOT_FOUND
                ));
    }


}