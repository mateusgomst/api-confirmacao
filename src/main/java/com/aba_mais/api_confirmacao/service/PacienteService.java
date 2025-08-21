package com.aba_mais.api_confirmacao.service;

import com.aba_mais.api_confirmacao.dto.CreatePacienteRequestDto;
import com.aba_mais.api_confirmacao.entity.Paciente;
import com.aba_mais.api_confirmacao.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

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