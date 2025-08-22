package com.aba_mais.api_confirmacao.controllers;

import com.aba_mais.api_confirmacao.dtos.AgendamentoResponseDto;
import com.aba_mais.api_confirmacao.dtos.CriarAgendamentoRequestDto;
import com.aba_mais.api_confirmacao.entities.Agendamento;
import com.aba_mais.api_confirmacao.interfaces.AgendamentoServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoServiceInterface agendamentoService;

    @PostMapping
    public ResponseEntity<Agendamento> agendarConsulta(@Validated @RequestBody CriarAgendamentoRequestDto agendamentoDto) {
        Agendamento agendamento = agendamentoService.criarAgendamento(
                agendamentoDto.getPacienteId(),
                agendamentoDto.getDataHora()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamento);
    }

    @GetMapping()
    public ResponseEntity<List<AgendamentoResponseDto>> listarAgendamentos() {
        return ResponseEntity.ok(agendamentoService.listarAgendamentos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDto> buscarAgendamentoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoService.buscarAgendamentoPorId(id));
    }

    @GetMapping("/paciente/{id}")
    public ResponseEntity<List<AgendamentoResponseDto>> buscarAgendamentosPorPacienteId(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoService.listarAgendamentosPorPacienteId(id));
    }
}