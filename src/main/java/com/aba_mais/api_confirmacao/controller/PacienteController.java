package com.aba_mais.api_confirmacao.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pacientes")

public class PacienteController{

    @PostMapping()
    public ResponseEntity<String> cadastrarPaciente(){
        return ResponseEntity.ok("Paciente cadastrado com sucesso!");
    }
}
