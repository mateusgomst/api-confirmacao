package com.aba_mais.api_confirmacao.repositories;

import com.aba_mais.api_confirmacao.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByNome(String nome);

    Optional<Paciente> findByNomeAndEmailResponsavelAndTelefoneResponsavel(String nome, String emailResponsavel, String telefoneResponsavel);
}

