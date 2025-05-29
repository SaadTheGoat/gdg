package ma.emsi.projetgza.metier;


import ma.emsi.projetgza.dao.models.Reparateur;

import java.util.List;
import java.util.Optional;

public interface IReparateurService {
    Optional<Reparateur> findByEmail(String email);
    Optional<Reparateur> findProprietaire();
    Reparateur save(Reparateur reparateur);
    public List<Reparateur> findAll() ;
}
