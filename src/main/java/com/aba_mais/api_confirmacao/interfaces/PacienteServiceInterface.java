package com.aba_mais.api_confirmacao.interfaces;

import java.util.List;

import com.aba_mais.api_confirmacao.dtos.AtualizarPacienteRequestDto;
import com.aba_mais.api_confirmacao.dtos.CriarPacienteRequestDto;
import com.aba_mais.api_confirmacao.dtos.PacienteResponseDto;
import com.aba_mais.api_confirmacao.entities.Paciente;

public interface PacienteServiceInterface {
    PacienteResponseDto atualizarPaciente(Long id, AtualizarPacienteRequestDto pacienteDto);
    void deletarPaciente(Long id);
    PacienteResponseDto cadastrarPaciente(CriarPacienteRequestDto pacienteDto);
    List<Paciente> listarPacientes();
    Paciente buscarPacientePorNome(String nome);
}