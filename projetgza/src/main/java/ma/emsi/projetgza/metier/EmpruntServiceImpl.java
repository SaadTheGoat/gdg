package ma.emsi.projetgza.metier;

import ma.emsi.projetgza.dao.models.Emprunt;
import ma.emsi.projetgza.dao.repositories.EmpruntRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpruntServiceImpl implements IEmpruntService {

    @Autowired
    private EmpruntRepositorie empruntRepository;

    @Override
    public List<Emprunt> findByReparateurId(Long reparateurId) {
        return empruntRepository.findByReparateur_Id(reparateurId);
    }

    @Override
    public Emprunt save(Emprunt emprunt) {
        return empruntRepository.save(emprunt);
    }

    @Override
    public Optional<Emprunt> findById(Long id) {
        return empruntRepository.findById(id);
    }


    @Override
    public double totalEmpruntsNonRegles(Long reparateurId) {
        return empruntRepository.findByReparateur_Id(reparateurId).stream()
                .filter(e -> e.getEmp().toString().equals("NON_REGLE"))
                .mapToDouble(Emprunt::getMontant)
                .sum();
    }
}
