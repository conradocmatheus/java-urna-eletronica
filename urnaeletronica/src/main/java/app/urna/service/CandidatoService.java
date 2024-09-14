package app.urna.service;

import app.urna.entity.Candidato;
import app.urna.entity.Enum.StatusCandidato;
import app.urna.handler.exception.NotFoundException;
import app.urna.repository.CandidatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidatoService {

    @Autowired
    private CandidatoRepository candidatoRepository;


    // Funcao que inativa o candidato
    public void inativarCandidato(Long id) {

        // Verifica se o candidato existe por id
        Candidato candidato = candidatoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidato nao encontrado"));

        // Inativa e salva o candidato
        candidato.setStatus(StatusCandidato.INATIVO);
        candidatoRepository.save(candidato);
    }

    // Salvar candidato
    public Candidato salvarCandidato(Candidato candidato) {
        candidato.setStatus(StatusCandidato.ATIVO);
        return candidatoRepository.save(candidato);
    }

    // Atualizar candidato
    public Candidato atualizarCandidato(Long id, Candidato candidatoAtualizado) {
        // Verifica se o candidato existe por id
        Candidato candidato = candidatoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidato nao encontrado"));

        // Atualiza os campos do candidato existente
        candidato.setNomeCompleto(candidatoAtualizado.getNomeCompleto());
        candidato.setCpf(candidatoAtualizado.getCpf());
        candidato.setNumeroCandidato(candidatoAtualizado.getNumeroCandidato());
        candidato.setFuncao(candidatoAtualizado.getFuncao());

        return candidatoRepository.save(candidato);
    }

    // Lista os candidatos ATIVOS
    public List<Candidato> listarCandidatos() {
        return candidatoRepository.findAllByStatus(StatusCandidato.ATIVO);
    }

    // Lista os candidatos a vereador
    public List<Candidato> listarCandidatosVereadores() {
        return candidatoRepository.getAllByFuncao(2);
    }

    // Lista os candidados a prefeito
    public List<Candidato> listarCandidatosPrefeitos() {
        return candidatoRepository.getAllByFuncao(1);
    }
}
