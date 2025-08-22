package com.aba_mais.api_confirmacao.interfaces;

import java.util.List;
import com.aba_mais.api_confirmacao.dtos.CreatePacienteRequestDto;
import com.aba_mais.api_confirmacao.entities.Paciente;

public interface PacienteServiceInterface {
    Paciente cadastrarPaciente(CreatePacienteRequestDto pacienteDto);
    List<Paciente> listarPacientes();
    Paciente buscarPacientePorNome(String nome);
}