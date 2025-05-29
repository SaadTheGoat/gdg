package ma.emsi.projetgza.dao.repositories;

import ma.emsi.projetgza.dao.models.Recette;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecetteRepositorie extends JpaRepository<Recette , Long> {
    List<Recette> findByReparateur_Id(Long reparateurId);

    Optional<Recette> findByReparationId(Long id);


}
