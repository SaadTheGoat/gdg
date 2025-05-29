package ma.emsi.projetgza.presentation.Proprietaire;

import ma.emsi.projetgza.dao.models.Reparateur;
import ma.emsi.projetgza.dao.models.Reparation;
import ma.emsi.projetgza.enums.Role;
import ma.emsi.projetgza.metier.IReparateurService;
import ma.emsi.projetgza.metier.IReparationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProprietaireController {

    @Autowired
    private  IReparateurService reparateurService;


    @Autowired
    private IReparationService reparationService;



    @GetMapping("/dashboard")
    public String showDashboard(@RequestParam(name = "keyword", required = false) String keyword, Model model) {

        // 1. Charger tous les réparateurs (sauf le propriétaire)
        List<Reparateur> reparateurs = reparateurService.findAll()
                .stream()
                .filter(r -> r.getRole() == Role.REPARATEUR)
                .toList();

        // 2. Charger toutes les réparations
        List<Reparation> reparations = reparationService.findAll();

        // 3. Filtrage si mot-clé fourni (code suivi ou nom du réparateur)
        if (keyword != null && !keyword.isBlank()) {
            String kw = keyword.toLowerCase();
            reparations = reparations.stream()
                    .filter(rep ->
                            (rep.getCodeSuivi() != null && rep.getCodeSuivi().toLowerCase().contains(kw)) ||
                                    (rep.getReparateur() != null && rep.getReparateur().getNom().toLowerCase().contains(kw))
                    )
                    .toList();
        }

        // 4. Calculer les recettes par réparateur
        Map<Long, Double> recettesParReparateur = new HashMap<>();
        for (Reparateur r : reparateurs) {
            double total = r.getRecettes() != null
                    ? r.getRecettes().stream()
                    .mapToDouble(recette -> recette.getMontant() != null ? recette.getMontant() : 0.0)
                    .sum()
                    : 0.0;
            recettesParReparateur.put(r.getId(), total);
        }

        // 5. Envoyer les données à la vue
        model.addAttribute("reparateurs", reparateurs);
        model.addAttribute("reparations", reparations);
        model.addAttribute("recettesParReparateur", recettesParReparateur);
        model.addAttribute("nouveauReparateur", new Reparateur());
        model.addAttribute("keyword", keyword);

        return "dashboard"; // dashboard.html
    }


}

