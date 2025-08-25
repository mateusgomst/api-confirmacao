package com.aba_mais.api_confirmacao.repositories;

import com.aba_mais.api_confirmacao.entities.Agendamento;
import com.aba_mais.api_confirmacao.entities.StatusAgendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    Optional<Agendamento> findByTokenConfirmacao(String token);
    List<Agendamento> findAllByPacienteId(Long pacienteId);
    Optional<Agendamento> findByPacienteIdAndDataHora(Long pacienteId, LocalDateTime dataHora);

}