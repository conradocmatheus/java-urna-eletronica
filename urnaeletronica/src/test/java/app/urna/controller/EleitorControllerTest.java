package app.urna.controller;

import app.urna.entity.Eleitor;
import app.urna.handler.exception.NotFoundException;
import app.urna.service.EleitorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EleitorControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private EleitorController eleitorController;

    @Mock
    private EleitorService eleitorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(eleitorController).build();
    }


    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testInativarEleitor_DeveRetornarSucesso() throws Exception {
        Long eleitorId = 1L;
        doNothing().when(eleitorService).inativarEleitor(eleitorId);

        mockMvc.perform(put("/api/eleitores/inativar/{id}", eleitorId))
                .andExpect(status().isOk())
                .andExpect(content().string("Eleitor inativado com sucesso"));

        verify(eleitorService, times(1)).inativarEleitor(eleitorId);
    }

    @Test
    public void testInativarEleitor_NotFound() throws Exception {
        Long eleitorId = 1L;
        doThrow(new NotFoundException("Eleitor não encontrado")).when(eleitorService).inativarEleitor(eleitorId);

        mockMvc.perform(put("/api/eleitores/inativar/{id}", eleitorId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Eleitor não encontrado"));

        verify(eleitorService, times(1)).inativarEleitor(eleitorId);
    }

    @Test
    public void testSalvarEleitor_DeveRetornarEleitorCriado() throws Exception {
        Eleitor novoEleitor = new Eleitor();
        novoEleitor.setNome("João Silva");
        novoEleitor.setCpf("12345678910");

        when(eleitorService.salvarEleitor(any(Eleitor.class))).thenReturn(novoEleitor);

        mockMvc.perform(post("/api/eleitores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(novoEleitor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.cpf").value("12345678910"));

        verify(eleitorService, times(1)).salvarEleitor(any(Eleitor.class));
    }

    @Test
    public void testAtualizarEleitor_DeveRetornarEleitorAtualizado() throws Exception {
        Long eleitorId = 1L;
        Eleitor eleitorAtualizado = new Eleitor();
        eleitorAtualizado.setNome("Maria Souza");
        eleitorAtualizado.setCpf("09876543210");

        when(eleitorService.atualizarEleitor(eq(eleitorId), any(Eleitor.class))).thenReturn(eleitorAtualizado);

        mockMvc.perform(put("/api/eleitores/{id}", eleitorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(eleitorAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Maria Souza"))
                .andExpect(jsonPath("$.cpf").value("09876543210"));

        verify(eleitorService, times(1)).atualizarEleitor(eq(eleitorId), any(Eleitor.class));
    }

    @Test
    public void testListarEleitoresAptos_DeveRetornarListaDeEleitores() throws Exception {
        Eleitor eleitor1 = new Eleitor();
        eleitor1.setNome("João Silva");

        Eleitor eleitor2 = new Eleitor();
        eleitor2.setNome("Maria Souza");

        List<Eleitor> eleitores = Arrays.asList(eleitor1, eleitor2);

        when(eleitorService.listarEleitores()).thenReturn(eleitores);

        mockMvc.perform(get("/api/eleitores/aptos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João Silva"))
                .andExpect(jsonPath("$[1].nome").value("Maria Souza"));

        verify(eleitorService, times(1)).listarEleitores();
    }
}
