package com.aba_mais.api_confirmacao.controllers;

import com.aba_mais.api_confirmacao.interfaces.AgendamentoServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoServiceInterface agendamentoService;

    @PostMapping
    public String agendarConsulta() {
        // Implementar lógica de agendamento de consulta
        return "Consulta agendada com sucesso!";
    }
}
