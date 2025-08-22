package com.aba_mais.api_confirmacao.repositories;

import com.aba_mais.api_confirmacao.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    boolean existsByEmailResponsavel(String email);
    boolean existsByNome(String nome);

    Paciente findByNome(String nome);


}
