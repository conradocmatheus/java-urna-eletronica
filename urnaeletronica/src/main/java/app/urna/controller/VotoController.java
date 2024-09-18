import app.urna.entity.Voto;
import app.urna.handler.exception.NotFoundException;
import app.urna.handler.exception.WrongCandidateException;
import app.urna.service.VotoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(VotoControllerTest.class)
public class VotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private VotoService votoService;

    @InjectMocks
    private VotoController votoController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void votar_DeveRetornarStatusCriadoComHash() throws Exception {
        Voto voto = new Voto(); // Configure o objeto Voto conforme necessário
        String hash = "someHash";

        // Configura o comportamento do serviço
        when(votoService.votar(any(Voto.class), anyLong())).thenReturn(hash);

        // Realiza a requisição e verifica a resposta
        mockMvc.perform(post("/api/votos/votar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(voto)))
                .andExpect(status().isCreated())
                .andExpect(content().string(hash));
    }

    @Test
    public void votar_DeveRetornarStatusBadRequestQuandoNotFoundException() throws Exception {
        Voto voto = new Voto(); // Configure o objeto Voto conforme necessário

        // Configura o comportamento do serviço
        when(votoService.votar(any(Voto.class), anyLong()))
                .thenThrow(new NotFoundException("Eleitor não encontrado"));

        // Realiza a requisição e verifica a resposta
        mockMvc.perform(post("/api/votos/votar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(voto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Eleitor não encontrado"));
    }

    @Test
    public void votar_DeveRetornarStatusBadRequestQuandoInvalidParameterException() throws Exception {
        Voto voto = new Voto(); // Configure o objeto Voto conforme necessário

        // Configura o comportamento do serviço
        when(votoService.votar(any(Voto.class), anyLong()))
                .thenThrow(new InvalidParameterException("Eleitor inapto para votação"));

        // Realiza a requisição e verifica a resposta
        mockMvc.perform(post("/api/votos/votar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(voto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Eleitor inapto para votação"));
    }

    @Test
    public void votar_DeveRetornarStatusBadRequestQuandoWrongCandidateException() throws Exception {
        Voto voto = new Voto(); // Configure o objeto Voto conforme necessário

        // Configura o comportamento do serviço
        when(votoService.votar(any(Voto.class), anyLong()))
                .thenThrow(new WrongCandidateException("Candidato inválido"));

        // Realiza a requisição e verifica a resposta
        mockMvc.perform(post("/api/votos/votar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(voto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Candidato inválido"));
    }

    @Test
    public void votar_DeveRetornarStatusInternalServerErrorQuandoException() throws Exception {
        Voto voto = new Voto(); // Configure o objeto Voto conforme necessário

        // Configura o comportamento do serviço
        when(votoService.votar(any(Voto.class), anyLong()))
                .thenThrow(new RuntimeException("Erro interno"));

        // Realiza a requisição e verifica a resposta
        mockMvc.perform(post("/api/votos/votar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(voto)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Erro interno do servidor"));
    }
}
