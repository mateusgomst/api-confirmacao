package com.aba_mais.api_confirmacao.controller;

import com.aba_mais.api_confirmacao.dto.CreatePacienteRequestDto;
import com.aba_mais.api_confirmacao.entity.Paciente;
import com.aba_mais.api_confirmacao.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<Paciente> cadastrarPaciente(@RequestBody CreatePacienteRequestDto paciente) {
        return ResponseEntity.ok(pacienteService.cadastrarPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes() {
        return ResponseEntity.ok(pacienteService.listarPacientes());
    }
}