package ma.emsi.projetgza.metier;

import ma.emsi.projetgza.dao.models.Reparation;

import java.util.List;
import java.util.Optional;

public interface IReparationService {
    List<Reparation> findAll();
    Reparation saveWithAppareils(Reparation reparation);

    List<Reparation> findByReparateurId(Long reparateurId);

    public Reparation save(Reparation reparation);

    Optional<Reparation> getReparationByCodeSuivi(String codeSuivi);

    Reparation findById(Long id);
    void deleteById(Long id);

}

