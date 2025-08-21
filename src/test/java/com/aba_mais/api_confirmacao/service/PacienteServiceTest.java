package com.aba_mais.api_confirmacao.service;

import com.aba_mais.api_confirmacao.dto.CreatePacienteRequestDto;
import com.aba_mais.api_confirmacao.entity.Paciente;
import com.aba_mais.api_confirmacao.exceptions.EmailJaExisteException;
import com.aba_mais.api_confirmacao.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteService pacienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaExiste() {
        // Arrange
        String email = "test@example.com";
        CreatePacienteRequestDto pacienteDto = new CreatePacienteRequestDto(
                "João Silva", email, "11999999999"
        );
        
        when(pacienteRepository.existsByEmailResponsavel(email)).thenReturn(true);

        // Act & Assert
        EmailJaExisteException exception = assertThrows(
                EmailJaExisteException.class,
                () -> pacienteService.cadastrarPaciente(pacienteDto)
        );

        assertEquals(email, exception.getEmail());
        assertEquals("Email já existe: " + email, exception.getMessage());
        verify(pacienteRepository, never()).save(any());
    }

    @Test
    void deveCadastrarPacienteQuandoEmailNaoExiste() {
        // Arrange
        String email = "novo@example.com";
        CreatePacienteRequestDto pacienteDto = new CreatePacienteRequestDto(
                "Maria Silva", email, "11888888888"
        );
        
        Paciente pacienteEsperado = new Paciente("Maria Silva", email, "11888888888");
        
        when(pacienteRepository.existsByEmailResponsavel(email)).thenReturn(false);
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(pacienteEsperado);

        // Act
        Paciente resultado = pacienteService.cadastrarPaciente(pacienteDto);

        // Assert
        assertNotNull(resultado);
        verify(pacienteRepository).existsByEmailResponsavel(email);
        verify(pacienteRepository).save(any(Paciente.class));
    }
}