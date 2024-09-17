package app.urna.repository;

import app.urna.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VotoRepository extends JpaRepository<Voto, Long> {

    @Query("SELECT COUNT(v) FROM Voto v WHERE v.prefeitoEscolhido.id = :idCandidato")
    int contarVotosPrefeito(Long idCandidato);

    @Query("SELECT COUNT(v) FROM Voto v WHERE v.vereadorEscolhido.id = :idCandidato")
    int contarVotosVereador(Long idCandidato);
}
