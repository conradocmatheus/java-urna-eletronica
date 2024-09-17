package app.urna.service;

import app.urna.entity.Eleitor;
import app.urna.entity.Enum.StatusEleitor;
import app.urna.handler.exception.BussinessException;
import app.urna.handler.exception.NotFoundException;
import app.urna.repository.EleitorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EleitorServiceTest {

    @Mock
    private EleitorRepository eleitorRepository;

    @InjectMocks
    private EleitorService eleitorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void inativarEleitor_DeveInativarEleitorQuandoNaoVotou() {
        Eleitor eleitor = new Eleitor();
        eleitor.setId(1L);
        eleitor.setStatus(StatusEleitor.APTO);

        when(eleitorRepository.findById(1L)).thenReturn(Optional.of(eleitor));

        eleitorService.inativarEleitor(1L);

        assertEquals(StatusEleitor.INATIVO, eleitor.getStatus());
        verify(eleitorRepository, times(1)).save(eleitor);
    }

    @Test
    void inativarEleitor_DeveLancarExcecaoQuandoEleitorJaVotou() {
        Eleitor eleitor = new Eleitor();
        eleitor.setId(1L);
        eleitor.setStatus(StatusEleitor.VOTOU);

        when(eleitorRepository.findById(1L)).thenReturn(Optional.of(eleitor));

        BussinessException exception = assertThrows(BussinessException.class, () -> eleitorService.inativarEleitor(1L));
        assertEquals("Eleitor ja votou. Impossivel inativar", exception.getMessage());
        verify(eleitorRepository, never()).save(eleitor);
    }

    @Test
    void inativarEleitor_DeveLancarExcecaoQuandoEleitorNaoForEncontrado() {
        when(eleitorRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> eleitorService.inativarEleitor(1L));
        assertEquals("Eleitor nao encontrado", exception.getMessage());
        verify(eleitorRepository, never()).save(any(Eleitor.class));
    }

    @Test
    void salvarEleitor_DeveSalvarEleitorValido() {
        Eleitor eleitor = new Eleitor();
        eleitor.setId(1L);
        eleitor.setCpf("12345678910");
        eleitor.setEmail("devgraciota@gmail.com");
        eleitor.setStatus(StatusEleitor.APTO);

        when(eleitorRepository.save(eleitor)).thenReturn(eleitor);

        Eleitor resultado = eleitorService.salvarEleitor(eleitor);

        assertNotNull(resultado);
        assertEquals(StatusEleitor.APTO, resultado.getStatus());
        verify(eleitorRepository, times(1)).save(eleitor);
    }

    @Test
    void atualizarEleitor_DeveAtualizarEleitorExistente() {
        Long id = 1L;
        Eleitor eleitorExistente = new Eleitor();
        eleitorExistente.setId(id);
        eleitorExistente.setNomeCompleto("Antigo Nome");
        eleitorExistente.setCpf("12345678900");
        eleitorExistente.setEmail("antigo@email.com");
        eleitorExistente.setTelefoneCelular("1111111111");
        eleitorExistente.setTelefoneFixo("2222222222");
        eleitorExistente.setProfissao("Antiga Profissao");

        Eleitor eleitorAtualizado = new Eleitor();
        eleitorAtualizado.setNomeCompleto("Novo Nome");
        eleitorAtualizado.setCpf("98765432100");
        eleitorAtualizado.setEmail("novo@email.com");
        eleitorAtualizado.setTelefoneCelular("3333333333");
        eleitorAtualizado.setTelefoneFixo("4444444444");
        eleitorAtualizado.setProfissao("Nova Profissao");

        when(eleitorRepository.findById(id)).thenReturn(Optional.of(eleitorExistente));
        when(eleitorRepository.save(eleitorExistente)).thenReturn(eleitorExistente);

        Eleitor eleitorResultado = eleitorService.atualizarEleitor(id, eleitorAtualizado);

        assertEquals("Novo Nome", eleitorResultado.getNomeCompleto());
        assertEquals("98765432100", eleitorResultado.getCpf());
        assertEquals("novo@email.com", eleitorResultado.getEmail());
        assertEquals("3333333333", eleitorResultado.getTelefoneCelular());
        assertEquals("4444444444", eleitorResultado.getTelefoneFixo());
        assertEquals("Nova Profissao", eleitorResultado.getProfissao());

        verify(eleitorRepository, times(1)).findById(id);
        verify(eleitorRepository, times(1)).save(eleitorExistente);
    }

    @Test
    void atualizarEleitor_DeveLancarNotFoundExceptionQuandoEleitorNaoExiste() {
        Long id = 1L;
        Eleitor eleitorAtualizado = new Eleitor();
        eleitorAtualizado.setNomeCompleto("Novo Nome");
        eleitorAtualizado.setCpf("98765432100");

        when(eleitorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> eleitorService.atualizarEleitor(id, eleitorAtualizado));

        verify(eleitorRepository, times(1)).findById(id);
        verify(eleitorRepository, never()).save(any(Eleitor.class));
    }

    @Test
    void listarEleitores_DeveRetornarListaDeEleitoresAptos() {
        List<Eleitor> eleitoresAptos = new ArrayList<>();
        Eleitor eleitor = new Eleitor();
        eleitor.setNomeCompleto("Eleitor 1");
        eleitor.setStatus(StatusEleitor.APTO);
        eleitoresAptos.add(eleitor);

        when(eleitorRepository.findAllByStatus(StatusEleitor.APTO)).thenReturn(eleitoresAptos);

        List<Eleitor> resultado = eleitorService.listarEleitores();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(StatusEleitor.APTO, resultado.get(0).getStatus());

        verify(eleitorRepository, times(1)).findAllByStatus(StatusEleitor.APTO);
    }

    @Test
    void listarEleitores_DeveLancarExceptionQuandoNenhumEleitorEncontrado() {
        when(eleitorRepository.findAllByStatus(StatusEleitor.APTO)).thenReturn(new ArrayList<>());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> eleitorService.listarEleitores());

        assertEquals("404 NOT_FOUND \"Nenhum eleitor encontrado\"", exception.getMessage());
        verify(eleitorRepository, times(1)).findAllByStatus(StatusEleitor.APTO);
    }
}
