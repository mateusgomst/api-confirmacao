package com.aba_mais.api_confirmacao.repositories;

import com.aba_mais.api_confirmacao.entities.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
}
