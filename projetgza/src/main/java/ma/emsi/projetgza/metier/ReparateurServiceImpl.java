package ma.emsi.projetgza.metier;



import ma.emsi.projetgza.dao.models.Reparateur;
import ma.emsi.projetgza.dao.repositories.ReparateurRepositorie;
import ma.emsi.projetgza.enums.Role;
import ma.emsi.projetgza.metier.IReparateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReparateurServiceImpl implements IReparateurService {

    @Autowired
    private  ReparateurRepositorie repository;



    @Override
    public Optional<Reparateur> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Optional<Reparateur> findProprietaire() {
        return repository.findByRole(Role.PROPRIETAIRE);
    }

    @Override
    public Reparateur save(Reparateur reparateur) {
        return repository.save(reparateur);
    }

    // ➕ Méthode utile pour dashboard
    public List<Reparateur> findAll() {
        return repository.findAll();
    }
}


