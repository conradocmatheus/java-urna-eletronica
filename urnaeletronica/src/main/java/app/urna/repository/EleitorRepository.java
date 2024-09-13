package app.urna.repository;

import app.urna.entity.Eleitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EleitorRepository extends JpaRepository<Eleitor, Long> {
    List<Eleitor> FindAllByStatus(String status);
}
