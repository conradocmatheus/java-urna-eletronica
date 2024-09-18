package app.urna.service;

import app.urna.entity.Candidato;
import app.urna.entity.Enum.StatusCandidato;
import app.urna.handler.exception.NotFoundException;
import app.urna.repository.CandidatoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CandidatoServiceTest {

    @InjectMocks
    private CandidatoService candidatoService;

    @Mock
    private CandidatoRepository candidatoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInativarCandidato_Success() {
        Long candidatoId = 1L;
        Candidato candidato = new Candidato();
        candidato.setId(candidatoId);
        candidato.setStatus(StatusCandidato.ATIVO);
        when(candidatoRepository.findById(candidatoId)).thenReturn(Optional.of(candidato));
        candidatoService.inativarCandidato(candidatoId);
        verify(candidatoRepository).save(candidato);
        assert(candidato.getStatus() == StatusCandidato.INATIVO);
    }

    @Test
    public void testInativarCandidato_NotFound() {
        Long candidatoId = 1L;
        when(candidatoRepository.findById(candidatoId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            candidatoService.inativarCandidato(candidatoId);
        });
        verify(candidatoRepository, never()).save(any(Candidato.class));
    }

    @Test
    void salvarCandidato_DeveRetornarCandidatoComStatusAtivo() {
        // Arrange
        Candidato candidato = new Candidato();
        candidato.setNomeCompleto("João Silva");
        candidato.setCpf("12345678910");
        candidato.setNumeroCandidato("123");
        candidato.setFuncao(1);

        when(candidatoRepository.save(any(Candidato.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Candidato resultado = candidatoService.salvarCandidato(candidato);

        // Assert
        assertNotNull(resultado);
        assertEquals(StatusCandidato.ATIVO, resultado.getStatus());
        assertEquals("João Silva", resultado.getNomeCompleto());
        verify(candidatoRepository, times(1)).save(any(Candidato.class));
    }

    @Test
    void atualizarCandidato_DeveAtualizarCandidatoExistente() {
        Long id = 1L;
        Candidato candidatoExistente = new Candidato();
        candidatoExistente.setId(id);
        candidatoExistente.setNomeCompleto("Antigo Nome");
        candidatoExistente.setCpf("12345678910");
        candidatoExistente.setNumeroCandidato("123");
        candidatoExistente.setFuncao(1);

        Candidato candidatoAtualizado = new Candidato();
        candidatoAtualizado.setNomeCompleto("Novo Nome");
        candidatoAtualizado.setCpf("09876543210");
        candidatoAtualizado.setNumeroCandidato("456");
        candidatoAtualizado.setFuncao(2);

        when(candidatoRepository.findById(id)).thenReturn(Optional.of(candidatoExistente));
        when(candidatoRepository.save(any(Candidato.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Candidato resultado = candidatoService.atualizarCandidato(id, candidatoAtualizado);

        assertNotNull(resultado);
        assertEquals("Novo Nome", resultado.getNomeCompleto());
        assertEquals("09876543210", resultado.getCpf());
        assertEquals("456", resultado.getNumeroCandidato());
        assertEquals(2, resultado.getFuncao());
        verify(candidatoRepository, times(1)).findById(id);
        verify(candidatoRepository, times(1)).save(any(Candidato.class));
    }

    @Test
    void listarCandidatos_DeveRetornarCandidatosAtivos() {
        Candidato candidato1 = new Candidato();
        candidato1.setNomeCompleto("Candidato 1");
        candidato1.setStatus(StatusCandidato.ATIVO);

        Candidato candidato2 = new Candidato();
        candidato2.setNomeCompleto("Candidato 2");
        candidato2.setStatus(StatusCandidato.ATIVO);

        when(candidatoRepository.findAllByStatus(StatusCandidato.ATIVO)).thenReturn(Arrays.asList(candidato1, candidato2));

        List<Candidato> resultado = candidatoService.listarCandidatos();

        assertEquals(2, resultado.size());
        assertEquals("Candidato 1", resultado.get(0).getNomeCompleto());
        assertEquals("Candidato 2", resultado.get(1).getNomeCompleto());
    }

    @Test
    void listarCandidatosVereadoresAtivos_DeveRetornarCandidatosVereadoresAtivos() {
        Candidato candidato1 = new Candidato();
        candidato1.setNomeCompleto("Vereador 1");
        candidato1.setFuncao(2);
        candidato1.setStatus(StatusCandidato.ATIVO);

        Candidato candidato2 = new Candidato();
        candidato2.setNomeCompleto("Vereador 2");
        candidato2.setFuncao(2);
        candidato2.setStatus(StatusCandidato.ATIVO);

        when(candidatoRepository.findAllByFuncaoAndStatus(2, StatusCandidato.ATIVO)).thenReturn(Arrays.asList(candidato1, candidato2));

        List<Candidato> resultado = candidatoService.listarCandidatosVereadoresAtivos();

        assertEquals(2, resultado.size());
        assertEquals("Vereador 1", resultado.get(0).getNomeCompleto());
        assertEquals("Vereador 2", resultado.get(1).getNomeCompleto());
    }

    @Test
    void listarCandidatosPrefeitosAtivos_DeveRetornarCandidatosPrefeitosAtivos() {
        Candidato candidato1 = new Candidato();
        candidato1.setNomeCompleto("Prefeito 1");
        candidato1.setFuncao(1);
        candidato1.setStatus(StatusCandidato.ATIVO);

        Candidato candidato2 = new Candidato();
        candidato2.setNomeCompleto("Prefeito 2");
        candidato2.setFuncao(1);
        candidato2.setStatus(StatusCandidato.ATIVO);

        when(candidatoRepository.findAllByFuncaoAndStatus(1, StatusCandidato.ATIVO)).thenReturn(Arrays.asList(candidato1, candidato2));

        List<Candidato> resultado = candidatoService.listarCandidatosPrefeitosAtivos();

        assertEquals(2, resultado.size());
        assertEquals("Prefeito 1", resultado.get(0).getNomeCompleto());
        assertEquals("Prefeito 2", resultado.get(1).getNomeCompleto());
    }
}
