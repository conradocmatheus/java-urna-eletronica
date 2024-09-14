package app.urna.service;

import app.urna.entity.Apuracao;
import app.urna.entity.Candidato;
import app.urna.entity.Eleitor;
import app.urna.entity.Enum.StatusEleitor;
import app.urna.entity.Voto;
import app.urna.handler.exception.NotFoundException;
import app.urna.handler.exception.WrongCandidateException;
import app.urna.repository.EleitorRepository;
import app.urna.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private EleitorRepository eleitorRepository;

    @Autowired
    private CandidatoService candidatoService;

    // Metodo de voto
    public String votar(Voto voto, Long idEleitor) {

        // Procura o eleitor por id
        Eleitor eleitor = eleitorRepository.findById(idEleitor)
                .orElseThrow(() -> new NotFoundException("Eleitor nao encontrado"));

        // Verifica se o eleitor esta APTO para votar
        if (!(eleitor.getStatus() == StatusEleitor.APTO)){
            throw new InvalidParameterException("“Eleitor inapto para votacao");
        }

        Candidato candidatoPrefeito = voto.getPrefeitoEscolhido();
        Candidato candidatoVereador = voto.getVereadorEscolhido();

        // Verifica se o candidato a prefeito e de fato um candidato a prefeito
        if (!(candidatoPrefeito.getFuncao() == 1)){
            throw new WrongCandidateException("O Candidato escolhido nao e um prefeito");
        }

        // Verifica se o candidato a vereador e de fato um candidato a vereador
        if (!(candidatoVereador.getFuncao() == 2)){
            throw new WrongCandidateException("O Candidato escolhido nao e um vereador");
        }

        // Seta a hora atual do voto
        voto.setDataHora(LocalDateTime.now());

        // Gera e define o hash de votacao
        String hash = UUID.randomUUID().toString();
        voto.setHash(hash);

        // Salva o voto no repositório
        votoRepository.save(voto);

        // Retorna o hash gerado
        return hash;
    }

    public Apuracao realizarApuracao() {

        // Pega a lista de prefeitos e vereadores
        List<Candidato> candidatosPrefeitos = candidatoService.listarCandidatosPrefeitosAtivos();
        List<Candidato> candidatosVereadores = candidatoService.listarCandidatosVereadoresAtivos();

        // Cria um novo objeto de apuracao
        Apuracao apuracao = new Apuracao();
        Map<Candidato, Integer> votosPrefeitos = new HashMap<>();
        Map<Candidato, Integer> votosVereadores = new HashMap<>();

        // Conta e seta votos para prefeitos
        for (Candidato candidato : candidatosPrefeitos) {
            // Quantidade de votos do prefeito por id
            int totalVotos = votoRepository.contarVotosPrefeito(candidato.getId());
            // Coloca o prefeito e os seus votos na lista votosPrefeitos
            votosPrefeitos.put(candidato, totalVotos);
            candidato.setVotosApurados(String.valueOf(totalVotos)); // Atribui ao campo transiente
        }

        // Conta e seta votos para vereadores
        for (Candidato candidato : candidatosVereadores) {
            // Quantidade de votos do vereador por id
            int totalVotos = votoRepository.contarVotosVereador(candidato.getId());
            // Coloca o vereador e os seus votos na lista votosVereadores
            votosVereadores.put(candidato, totalVotos);
            candidato.setVotosApurados(String.valueOf(totalVotos)); // Atribui ao campo transiente
        }

        // Ordena a lista de candidatos a prefeito pelo total de votos
        candidatosPrefeitos.sort((c1, c2) -> Integer.compare(
                Integer.parseInt(c2.getVotosApurados()), Integer.parseInt(c1.getVotosApurados())));

        // Ordena a lista de candidatos a vereador pelo total de votos
        candidatosVereadores.sort((c1, c2) -> Integer.compare(
                Integer.parseInt(c2.getVotosApurados()), Integer.parseInt(c1.getVotosApurados())));

        // Define o resultado da apuração
        apuracao.setVotosPrefeitos(votosPrefeitos);
        apuracao.setVotosVereadores(votosVereadores);

        return apuracao;
    }
}
