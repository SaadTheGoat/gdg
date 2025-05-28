package ma.emsi.projetgza.dao.repositories;

import ma.emsi.projetgza.dao.models.Emprunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpruntRepositorie extends JpaRepository<Emprunt , Long> {
}
