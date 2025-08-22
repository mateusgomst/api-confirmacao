package com.aba_mais.api_confirmacao.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.aba_mais.api_confirmacao.dtos.CreatePacienteRequestDto;
import com.aba_mais.api_confirmacao.entities.Paciente;
import com.aba_mais.api_confirmacao.interfaces.PacienteServiceInterface;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteServiceInterface pacienteService;

    @PostMapping
    public ResponseEntity<Paciente> cadastrarPaciente(@Validated @RequestBody CreatePacienteRequestDto paciente) {
        return ResponseEntity.ok(pacienteService.cadastrarPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes() {
        return ResponseEntity.ok(pacienteService.listarPacientes());
    }

    @GetMapping("/{nome}")
    public ResponseEntity<Paciente> buscarPacientePorNome(@PathVariable String nome) {
        Paciente pacientes = pacienteService.buscarPacientePorNome(nome);
        return ResponseEntity.ok(pacientes);
    }
}