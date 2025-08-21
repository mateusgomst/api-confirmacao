package com.aba_mais.api_confirmacao.controller;

import com.aba_mais.api_confirmacao.dto.CreatePacienteRequestDto;
import com.aba_mais.api_confirmacao.entity.Paciente;
import com.aba_mais.api_confirmacao.service.PacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pacientes")

public class PacienteController{

    private PacienteService pacienteService;
    @PostMapping()
    public ResponseEntity<Paciente> cadastrarPaciente(@RequestBody CreatePacienteRequestDto paciente) {
        return ResponseEntity.ok(pacienteService.cadastrarPaciente(paciente));
    }
}
