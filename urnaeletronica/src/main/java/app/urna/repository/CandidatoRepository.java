package app.urna.repository;

import app.urna.entity.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidatoRepository extends JpaRepository <Candidato,Long>{
    List<Candidato> FindByFuncao(Integer funcao);
    Candidato FindByNumero(Integer numero);
}
