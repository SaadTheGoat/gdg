package ma.emsi.projetgza.metier;

import ma.emsi.projetgza.dao.models.Emprunt;

import java.util.List;
import java.util.Optional;

public interface IEmpruntService {
    List<Emprunt> findByReparateurId(Long reparateurId);
    Emprunt save(Emprunt emprunt);
    Optional<Emprunt> findById(Long id);
    double totalEmpruntsNonRegles(Long reparateurId);

}

