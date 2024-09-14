package app.urna.service;


import app.urna.entity.Eleitor;
import app.urna.entity.Enum.StatusEleitor;
import app.urna.exception.BussinessException;
import app.urna.exception.NotFoundException;
import app.urna.repository.EleitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EleitorService {

    @Autowired
    private EleitorRepository eleitorRepository;


    // Funcao que inativa o eleitor
    public void inativarEleitor(Long id) {

        // Verifica se o eleitor existe por id
        Eleitor eleitor = eleitorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Eleitor nao encontrado"));

        // Verifica se o eleitor ja votou
        if (eleitor.getStatus() == StatusEleitor.VOTOU){
            throw new BussinessException("Eleitor ja votou. Impossivel inativar");
        }

        // Inativa e salva o eleitor
        eleitor.setStatus(StatusEleitor.INATIVO);
        eleitorRepository.save(eleitor);
    }
}
