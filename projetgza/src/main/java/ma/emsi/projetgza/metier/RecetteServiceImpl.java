package ma.emsi.projetgza.metier;

import ma.emsi.projetgza.dao.models.Recette;
import ma.emsi.projetgza.dao.repositories.RecetteRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecetteServiceImpl implements IrecetteService {

    @Autowired
    private RecetteRepositorie recetteRepository;

    @Override
    public List<Recette> findByReparateurId(Long id) {
        return recetteRepository.findByReparateur_Id(id);
    }

    @Override
    public double totalByReparateurId(Long id) {
        return recetteRepository.findByReparateur_Id(id)
                .stream()
                .mapToDouble(Recette::getMontant)
                .sum();
    }
}
