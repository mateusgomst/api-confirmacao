package com.aba_mais.api_confirmacao.service;

import com.aba_mais.api_confirmacao.dto.CreatePacienteRequestDto;
import com.aba_mais.api_confirmacao.entity.Paciente;
import com.aba_mais.api_confirmacao.repository.PacienteRepository;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {
    private PacienteRepository pacienteRepository;
    public Paciente cadastrarPaciente(CreatePacienteRequestDto pacienteDto){
        Paciente paciente = new Paciente(pacienteDto.getNome(), pacienteDto.getEmail(), pacienteDto.getTelefone());
        pacienteRepository.save(paciente);
        return paciente;
    }
}
