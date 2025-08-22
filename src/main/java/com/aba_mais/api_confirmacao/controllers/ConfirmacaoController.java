package com.aba_mais.api_confirmacao.controllers;

import com.aba_mais.api_confirmacao.dtos.ConfirmarAgendamentoDto;
import com.aba_mais.api_confirmacao.interfaces.ConfirmacaoServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/confirmacao")
public class ConfirmacaoController {

    @Autowired
    private ConfirmacaoServiceInterface confirmacaoService;

    @GetMapping("/{token}")
    public ResponseEntity<ConfirmarAgendamentoDto> confirmarAgendamento(@PathVariable String token) {
        ConfirmarAgendamentoDto response = confirmacaoService.confirmarAgendamento(token);
        return ResponseEntity.ok(response);
    }
}