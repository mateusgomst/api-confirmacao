package com.aba_mais.api_confirmacao.repository;

import com.aba_mais.api_confirmacao.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    
    boolean existsByEmailResponsavel(String emailResponsavel);
}
