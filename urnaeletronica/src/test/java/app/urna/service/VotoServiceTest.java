package app.urna.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import app.urna.entity.Apuracao;
import app.urna.entity.Candidato;
import app.urna.entity.Eleitor;
import app.urna.entity.Enum.StatusEleitor;
import app.urna.entity.Voto;
import app.urna.handler.exception.NotFoundException;
import app.urna.repository.CandidatoRepository;
import app.urna.repository.EleitorRepository;
import app.urna.repository.VotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.InvalidParameterException;
import java.util.*;

class VotoServiceTest {

    @Mock
    private EleitorRepository eleitorRepository;

    @Mock
    private CandidatoRepository candidatoRepository;

    @Mock
    private CandidatoService candidatoService;

    @Mock
    private VotoRepository votoRepository;

    @InjectMocks
    private VotoService votoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void votar_DeveLancarExceptionQuandoEleitorInapto() {
        Eleitor eleitor = new Eleitor();
        eleitor.setStatus(StatusEleitor.BLOQUEADO);

        Voto voto = new Voto();
        voto.setPrefeitoEscolhido(new Candidato());
        voto.setVereadorEscolhido(new Candidato());

        when(eleitorRepository.findById(anyLong())).thenReturn(Optional.of(eleitor));

        InvalidParameterException exception = assertThrows(InvalidParameterException.class, () ->
                votoService.votar(voto, 1L));
        assertEquals("Eleitor inapto para votacao", exception.getMessage());
    }

    @Test
    void votar_DeveLancarExceptionQuandoCandidatoPrefeitoInvalido() {
        Eleitor eleitor = new Eleitor();
        eleitor.setStatus(StatusEleitor.APTO);

        Candidato candidatoInvalido = new Candidato();
        candidatoInvalido.setFuncao(2);

        Candidato candidatoValido = new Candidato();
        candidatoValido.setFuncao(2);

        Voto voto = new Voto();
        voto.setPrefeitoEscolhido(candidatoInvalido);
        voto.setVereadorEscolhido(candidatoValido);

        when(eleitorRepository.findById(anyLong())).thenReturn(Optional.of(eleitor));
        when(candidatoRepository.findById(anyLong()))
                .thenReturn(Optional.of(candidatoInvalido))
                .thenReturn(Optional.of(candidatoValido));

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                votoService.votar(voto, 1L));
        assertEquals("Candidato a prefeito nao encontrado", exception.getMessage());
    }

    @Test
    void votar_DeveLancarExceptionQuandoCandidatoVereadorInvalido() {
        Eleitor eleitor = new Eleitor();
        eleitor.setStatus(StatusEleitor.APTO);

        Candidato candidatoPrefeito = new Candidato();
        candidatoPrefeito.setFuncao(1);

        Candidato candidatoInvalido = new Candidato();
        candidatoInvalido.setFuncao(1);

        Voto voto = new Voto();
        voto.setPrefeitoEscolhido(candidatoPrefeito);
        voto.setVereadorEscolhido(candidatoInvalido);

        when(eleitorRepository.findById(anyLong())).thenReturn(Optional.of(eleitor));
        when(candidatoRepository.findById(anyLong()))
                .thenReturn(Optional.of(candidatoPrefeito))
                .thenReturn(Optional.of(candidatoInvalido));

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                votoService.votar(voto, 1L));
        assertEquals("Candidato a prefeito nao encontrado", exception.getMessage());
    }

    @Test
    void votar_DeveLancarExceptionQuandoEleitorNaoEncontrado() {
        Voto voto = new Voto();
        voto.setPrefeitoEscolhido(new Candidato());
        voto.setVereadorEscolhido(new Candidato());

        when(eleitorRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                votoService.votar(voto, 1L));
        assertEquals("Eleitor nao encontrado", exception.getMessage());
    }

    @Test
    void votar_DeveLancarExceptionQuandoCandidatoPrefeitoNaoEncontrado() {
        Eleitor eleitor = new Eleitor();
        eleitor.setStatus(StatusEleitor.APTO);

        Voto voto = new Voto();
        voto.setPrefeitoEscolhido(new Candidato());
        voto.setVereadorEscolhido(new Candidato());

        when(eleitorRepository.findById(anyLong())).thenReturn(Optional.of(eleitor));
        when(candidatoRepository.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                votoService.votar(voto, 1L));
        assertEquals("Candidato a prefeito nao encontrado", exception.getMessage());
    }

    @Test
    void votar_DeveLancarExceptionQuandoCandidatoVereadorNaoEncontrado() {
        Eleitor eleitor = new Eleitor();
        eleitor.setStatus(StatusEleitor.APTO);

        Candidato candidatoPrefeito = new Candidato();
        candidatoPrefeito.setFuncao(1);

        Voto voto = new Voto();
        voto.setPrefeitoEscolhido(candidatoPrefeito);
        voto.setVereadorEscolhido(new Candidato());

        when(eleitorRepository.findById(anyLong())).thenReturn(Optional.of(eleitor));
        when(candidatoRepository.findById(anyLong()))
                .thenReturn(Optional.of(candidatoPrefeito))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                votoService.votar(voto, 1L));
        assertEquals("Candidato a prefeito nao encontrado", exception.getMessage());
    }

    @Test
    public void realizarApuracao_DeveRetornarApuracaoCorreta() {
        // Inicializa os mocks
        MockitoAnnotations.openMocks(this);

        // Mock dos candidatos
        Candidato candidatoPrefeito1 = new Candidato();
        candidatoPrefeito1.setId(1L);
        candidatoPrefeito1.setFuncao(1);

        Candidato candidatoPrefeito2 = new Candidato();
        candidatoPrefeito2.setId(2L);
        candidatoPrefeito2.setFuncao(1);

        Candidato candidatoVereador1 = new Candidato();
        candidatoVereador1.setId(3L);
        candidatoVereador1.setFuncao(2);

        Candidato candidatoVereador2 = new Candidato();
        candidatoVereador2.setId(4L);
        candidatoVereador2.setFuncao(2);

        // Mock da lista de candidatos
        List<Candidato> candidatosPrefeitos = Arrays.asList(candidatoPrefeito1, candidatoPrefeito2);
        List<Candidato> candidatosVereadores = Arrays.asList(candidatoVereador1, candidatoVereador2);

        when(candidatoService.listarCandidatosPrefeitosAtivos()).thenReturn(candidatosPrefeitos);
        when(candidatoService.listarCandidatosVereadoresAtivos()).thenReturn(candidatosVereadores);

        when(votoRepository.contarVotosPrefeito(1L)).thenReturn(100);
        when(votoRepository.contarVotosPrefeito(2L)).thenReturn(150);

        when(votoRepository.contarVotosVereador(3L)).thenReturn(200);
        when(votoRepository.contarVotosVereador(4L)).thenReturn(250);

        Apuracao apuracao = votoService.realizarApuracao();

        Map<Long, Integer> votosPrefeitosEsperado = new HashMap<>();
        votosPrefeitosEsperado.put(1L, 100);
        votosPrefeitosEsperado.put(2L, 150);

        Map<Long, Integer> votosVereadoresEsperado = new HashMap<>();
        votosVereadoresEsperado.put(3L, 200);
        votosVereadoresEsperado.put(4L, 250);

        int totalVotosEsperado = 100 + 150 + 200 + 250;

        assertEquals(votosPrefeitosEsperado, apuracao.getVotosPrefeitos());
        assertEquals(votosVereadoresEsperado, apuracao.getVotosVereadores());
        assertEquals(totalVotosEsperado, apuracao.getTotalVotos());
    }
}
