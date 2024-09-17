package app.urna.repository;

import app.urna.entity.Eleitor;
import app.urna.entity.Enum.StatusEleitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EleitorRepository extends JpaRepository<Eleitor, Long> {
    List<Eleitor> findAllByStatus(StatusEleitor statusEleitor);
}
