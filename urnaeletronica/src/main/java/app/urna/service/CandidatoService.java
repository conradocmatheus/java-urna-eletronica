package app.urna.service;

import app.urna.entity.Candidato;
import app.urna.entity.Enum.StatusCandidato;
import app.urna.exception.NotFoundException;
import app.urna.repository.CandidatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidatoService {

    @Autowired
    private CandidatoRepository candidatoRepository;

    public void inativarCandidato(Long id) {

        // Verifica se o candidato existe por id
        Candidato candidato = candidatoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidato nao encontrado"));


        candidato.setStatus(StatusCandidato.INATIVO);
    }
}
