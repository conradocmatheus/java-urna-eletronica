package app.urna.repository;

import app.urna.entity.Candidato;
import app.urna.entity.Enum.StatusCandidato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidatoRepository extends JpaRepository <Candidato,Long>{
    List<Candidato> findAllByStatus(StatusCandidato statusCandidato);
}
