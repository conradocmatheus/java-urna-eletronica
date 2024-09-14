package app.urna.service;

import app.urna.entity.Apuracao;
import app.urna.entity.Eleitor;
import app.urna.entity.Enum.StatusEleitor;
import app.urna.entity.Voto;
import app.urna.exception.NotFoundException;
import app.urna.repository.EleitorRepository;
import app.urna.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private EleitorRepository eleitorRepository;


    // Metodo de voto
    public String votar(Voto voto, Long idEleitor) {

        // Procura o eleitor por id
        Eleitor eleitor = eleitorRepository.findById(idEleitor)
                .orElseThrow(() -> new NotFoundException("Eleitor nao encontrado"));

        // Verifica se o eleitor esta APTO para votar
        if (!(eleitor.getStatus() == StatusEleitor.APTO)){
            throw new InvalidParameterException("“Eleitor inapto para votacao");
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
}
