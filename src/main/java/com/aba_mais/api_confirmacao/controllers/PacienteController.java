package com.aba_mais.api_confirmacao.controllers;

import java.util.List;

import com.aba_mais.api_confirmacao.dtos.AtualizarPacienteRequestDto;
import com.aba_mais.api_confirmacao.dtos.CriarPacienteRequestDto;
import com.aba_mais.api_confirmacao.dtos.PacienteResponseDto;
import com.aba_mais.api_confirmacao.entities.Paciente;
import com.aba_mais.api_confirmacao.interfaces.PacienteServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteServiceInterface pacienteService;

    @PostMapping
    public ResponseEntity<PacienteResponseDto> cadastrarPaciente(@Validated @RequestBody CriarPacienteRequestDto paciente) {
        PacienteResponseDto pacienteCadastrado = pacienteService.cadastrarPaciente(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteCadastrado);
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes() {
        return ResponseEntity.ok(pacienteService.listarPacientes());
    }

    @GetMapping("/{nome}")
    public ResponseEntity<Paciente> buscarPacientePorNome(@PathVariable String nome) {
        Paciente paciente = pacienteService.buscarPacientePorNome(nome);
        return ResponseEntity.ok(paciente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDto> atualizarPaciente(@PathVariable Long id, @Validated @RequestBody AtualizarPacienteRequestDto pacienteDto) {
        PacienteResponseDto atualizado = pacienteService.atualizarPaciente(id, pacienteDto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPaciente(@PathVariable Long id) {
        pacienteService.deletarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}
