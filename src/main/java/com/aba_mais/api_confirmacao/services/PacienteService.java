package com.aba_mais.api_confirmacao.services;

import java.util.List;

import com.aba_mais.api_confirmacao.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aba_mais.api_confirmacao.dtos.CreatePacienteRequestDto;
import com.aba_mais.api_confirmacao.entities.Paciente;
import com.aba_mais.api_confirmacao.interfaces.PacienteServiceInterface;
import com.aba_mais.api_confirmacao.repositories.PacienteRepository;

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
        if(pacienteRepository.existsByNome(paciente.getNome())){
            throw new PacienteJaExisteException(paciente.getNome());
        }

        if(paciente.getNome() == null || paciente.getNome().isEmpty() ){
            throw new NomeNullException(paciente.getNome());
        }

        if(paciente.getEmailResponsavel() == null || paciente.getEmailResponsavel().isEmpty()){
            throw new EmailNullException(paciente.getEmailResponsavel());
        }

        if(paciente.getTelefoneResponsavel() == null || paciente.getTelefoneResponsavel().isEmpty()){
            throw new TelefoneNullException(paciente.getTelefoneResponsavel());
        }


        return pacienteRepository.save(paciente);
    }

    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

    public Paciente buscarPacientePorNome(String nome) {
        Paciente paciente = pacienteRepository.findByNome(nome);
        if(paciente == null){
            throw new PacienteNaoExisteException(nome);
        }
        return paciente;
    }
}