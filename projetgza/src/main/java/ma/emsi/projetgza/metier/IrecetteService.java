package ma.emsi.projetgza.metier;

import ma.emsi.projetgza.dao.models.Recette;

import java.util.List;

public interface IrecetteService {
    List<Recette> findByReparateurId(Long id);
    double totalByReparateurId(Long id);


}

