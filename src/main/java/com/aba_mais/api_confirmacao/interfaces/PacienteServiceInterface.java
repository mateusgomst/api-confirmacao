package com.aba_mais.api_confirmacao.interfaces;
import java.util.List;

import com.aba_mais.api_confirmacao.dto.CreatePacienteRequestDto;
import com.aba_mais.api_confirmacao.entity.Paciente;


public interface PacienteServiceInterface{
    Paciente cadastrarPaciente(CreatePacienteRequestDto paciente);

    List<Paciente> listarPacientes();
}
