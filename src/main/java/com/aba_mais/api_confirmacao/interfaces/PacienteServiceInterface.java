package com.aba_mais.api_confirmacao.interfaces;

import java.util.List;
import com.aba_mais.api_confirmacao.dtos.CriarPacienteRequestDto;
import com.aba_mais.api_confirmacao.entities.Paciente;

public interface PacienteServiceInterface {
    Paciente cadastrarPaciente(CriarPacienteRequestDto pacienteDto);
    List<Paciente> listarPacientes();
    Paciente buscarPacientePorNome(String nome);
}