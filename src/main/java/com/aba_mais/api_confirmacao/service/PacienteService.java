package com.aba_mais.api_confirmacao.service;

import com.aba_mais.api_confirmacao.entity.Paciente;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {
    public Paciente cadastrarPaciente(Paciente paciente){
        return paciente;
    }
}
