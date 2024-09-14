package app.urna.service;


import app.urna.entity.Eleitor;
import app.urna.entity.Enum.StatusEleitor;
import app.urna.handler.exception.BussinessException;
import app.urna.handler.exception.NotFoundException;
import app.urna.repository.EleitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if (eleitor.getStatus() == StatusEleitor.VOTOU) {
            throw new BussinessException("Eleitor ja votou. Impossivel inativar");
        }

        // Inativa e salva o eleitor
        eleitor.setStatus(StatusEleitor.INATIVO);
        eleitorRepository.save(eleitor);
    }

    // Salvar Eleitor
    public Eleitor salvarEleitor(Eleitor eleitor) {
        validarStatusEleitor(eleitor);
        return eleitorRepository.save(eleitor);
    }

    // Atualizar Eleitor
    public Eleitor atualizarEleitor(Long id, Eleitor eleitorAtualizado) {
        Eleitor eleitor = eleitorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Eleitor não encontrado"));

        // Atualiza os campos do eleitor existente
        eleitor.setNomeCompleto(eleitorAtualizado.getNomeCompleto());
        eleitor.setCpf(eleitorAtualizado.getCpf());
        eleitor.setEmail(eleitorAtualizado.getEmail());
        eleitor.setTelefoneCelular(eleitorAtualizado.getTelefoneCelular());
        eleitor.setTelefoneFixo(eleitorAtualizado.getTelefoneFixo());
        eleitor.setProfissao(eleitorAtualizado.getProfissao());

        validarStatusEleitor(eleitor);

        return eleitorRepository.save(eleitor);
    }

    // Lista os eleitores com o status APTO
    public List<Eleitor> listarEleitores() {
        return eleitorRepository.findAllByStatus(StatusEleitor.APTO);
    }

    // Esse metodo valida o status do eleitor
    private void validarStatusEleitor(Eleitor eleitor) {
        // Variavel que verifica se ha pendencias cadastrais
        boolean eleitorComPendenciasCadastrais = false;

        // Verifica se o eleitor cadastrou email ou cpf
        if (eleitor.getCpf().isEmpty() || eleitor.getEmail().isEmpty()) {
            eleitor.setStatus(StatusEleitor.PENDENTE); // Definir status PENDENTE
            eleitorComPendenciasCadastrais = true;
        }

        // Verifica se o eleitor pode ter o status APTO
        if (!eleitorComPendenciasCadastrais
                && eleitor.getStatus() != StatusEleitor.INATIVO
                && eleitor.getStatus() != StatusEleitor.BLOQUEADO
                && eleitor.getStatus() != StatusEleitor.VOTOU) {
            eleitor.setStatus(StatusEleitor.APTO); // Eleitor apto
        }
    }

}
