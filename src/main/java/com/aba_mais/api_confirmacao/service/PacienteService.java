package com.aba_mais.api_confirmacao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aba_mais.api_confirmacao.dto.CreatePacienteRequestDto;
import com.aba_mais.api_confirmacao.entity.Paciente;
import com.aba_mais.api_confirmacao.interfaces.PacienteServiceInterface;
import com.aba_mais.api_confirmacao.repository.PacienteRepository;

@Service
public class PacienteService implements PacienteServiceInterface {

    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente cadastrarPaciente(CreatePacienteRequestDto pacienteDto) {
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
}