package app.urna.controller;

import app.urna.entity.Candidato;
import app.urna.service.CandidatoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CandidatoControllerTest {

    @Mock
    private CandidatoService candidatoService;

    @InjectMocks
    private CandidatoController candidatoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarCandidatos() {

        List<Candidato> candidatos = new ArrayList<>();
        candidatos.add(new Candidato());

        when(candidatoService.listarCandidatos()).thenReturn(candidatos);


        ResponseEntity<List<Candidato>> response = candidatoController.listarCandidatos();


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testInativarCandidato() {
        Long id = 1L;

        doNothing().when(candidatoService).inativarCandidato(id);


        ResponseEntity<String> response = candidatoController.inativarCandidato(id);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Candidato inativado com sucesso", response.getBody());

        verify(candidatoService, times(1)).inativarCandidato(id);
    }

    @Test
    void testSalvarCandidato() {
        Candidato candidato = new Candidato();
        when(candidatoService.salvarCandidato(any(Candidato.class))).thenReturn(candidato);

        ResponseEntity<Candidato> response = candidatoController.salvarCandidato(candidato);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(candidato, response.getBody());
    }
}
