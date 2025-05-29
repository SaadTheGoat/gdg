package ma.emsi.projetgza.metier;

import jakarta.transaction.Transactional;
import ma.emsi.projetgza.dao.models.Appareil;
import ma.emsi.projetgza.dao.models.Recette;
import ma.emsi.projetgza.dao.models.Reparation;
import ma.emsi.projetgza.dao.repositories.RecetteRepositorie;
import ma.emsi.projetgza.dao.repositories.ReparationRepositorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReparationServiceImpl implements IReparationService {

    @Autowired
    private  ReparationRepositorie reparationRepository;

    @Autowired
    private RecetteRepositorie recetteRepository;

    @Override
    public void deleteById(Long id) {
        reparationRepository.deleteById(id);
    }
    @Override
    public Reparation findById(Long id) {
        return reparationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réparation introuvable avec l'id : " + id));
    }

    @Override
    public List<Reparation> findAll() {
        return reparationRepository.findAll();
    }

    @Override
    @Transactional
    public Reparation saveWithAppareils(Reparation reparation) {
        // Ensure both sides are linked
        for (Appareil appareil : reparation.getAppareils()) {
            appareil.setReparation(reparation); // owning side
        }

        reparation.setCodeSuivi("REP-" + System.currentTimeMillis());
        reparation.setDateDebut(new Date());
        reparation.setReste(reparation.getCoutTotal() - reparation.getAvance());

        // Persist with cascading
        Reparation saved = reparationRepository.save(reparation);

        if (saved.getStatus() == Reparation.Status.CLOTUREE &&
                recetteRepository.findByReparationId(saved.getId()).isEmpty()) {

            Recette recette = new Recette();
            recette.setMontant(saved.getCoutTotal());
            recette.setDate(new Date());
            recette.setReparateur(saved.getReparateur());
            recette.setReparation(saved);

            recetteRepository.save(recette);
        }

        return saved;
    }




    @Override
    public List<Reparation> findByReparateurId(Long reparateurId) {
        return reparationRepository.findByReparateur_Id(reparateurId);
    }

    @Override
    public Reparation save(Reparation reparation) {
        boolean isCloture = reparation.getStatus() == Reparation.Status.CLOTUREE;

        // Enregistrer la réparation
        Reparation saved = reparationRepository.save(reparation);

        // Si clôturée et pas déjà une recette créée
        if (isCloture && recetteRepository.findByReparationId(saved.getId()).isEmpty()) {
            Recette recette = new Recette();
            recette.setMontant(saved.getCoutTotal()); // ou avance + reste
            recette.setDate(new Date());
            recette.setReparateur(saved.getReparateur());

            recetteRepository.save(recette);
        }

        return saved;
    }

    @Override
    public Optional<Reparation> getReparationByCodeSuivi(String codeSuivi) {
        return reparationRepository.findByCodeSuivi(codeSuivi);
    }

}

