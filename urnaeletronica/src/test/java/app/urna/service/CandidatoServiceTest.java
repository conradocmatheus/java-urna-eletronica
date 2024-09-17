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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
}
