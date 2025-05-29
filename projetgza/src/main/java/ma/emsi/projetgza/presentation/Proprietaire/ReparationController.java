package ma.emsi.projetgza.presentation.Proprietaire;

import jakarta.servlet.http.HttpSession;
import ma.emsi.projetgza.dao.models.Appareil;
import ma.emsi.projetgza.dao.models.Recette;
import ma.emsi.projetgza.dao.models.Reparateur;
import ma.emsi.projetgza.dao.models.Reparation;
import ma.emsi.projetgza.dao.repositories.RecetteRepositorie;
import ma.emsi.projetgza.enums.Role;
import ma.emsi.projetgza.metier.IReparateurService;
import ma.emsi.projetgza.metier.IReparationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/reparations")
public class ReparationController {

    @Autowired
    private  IReparationService reparationService;
    @Autowired
    private IReparateurService reparateurService ;
    @Autowired
    private RecetteRepositorie recetteRepository;

    // Étape 1 : Choix du nombre d'appareils
    @GetMapping("/choix")
    public String choisirNombre() {
        return "choisir-nb-appareils";
    }

    // Étape 2 : Formulaire dynamique
    @GetMapping("/nouvelle")
    public String showForm(@RequestParam("nb") int nb, Model model) {
        Reparation reparation = new Reparation();
        for (int i = 0; i < nb; i++) {
            reparation.getAppareils().add(new Appareil());
        }
        model.addAttribute("reparation", reparation);
        return "reparation-appareils";
    }

    @PostMapping("/suivant")
    public String details(@ModelAttribute("reparation") Reparation reparation, Model model) {
        model.addAttribute("reparateurs", reparateurService.findAll()
                .stream()
                .filter(r -> r.getRole() == Role.REPARATEUR)
                .toList());
        return "reparation-details";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("reparation") Reparation reparation, HttpSession session) {
        Reparateur user = (Reparateur) session.getAttribute("user");
        reparation.setReparateur(user); // 💥 LIGNE MANQUANTE !!!

        for (Appareil appareil : reparation.getAppareils()) {
            appareil.setReparation(reparation);
        }

        reparation.setCodeSuivi("REP-" + System.currentTimeMillis());
        reparation.setReste(reparation.getCoutTotal() - reparation.getAvance());

        reparationService.saveWithAppareils(reparation);

        double pourcentageNet = 100 - user.getCommision();  // ex: 100 - 30 = 70
        double montant = reparation.getCoutTotal() * (pourcentageNet / 100);

        Recette recette = new Recette();
        recette.setReparateur(user);
        recette.setMontant(montant);
        recette.setDate(new java.util.Date());
        recetteRepository.save(recette);

        if (user.getRole() == Role.REPARATEUR) {
            return "redirect:/reparateur/dashboard";
        } else {
            return "redirect:/dashboard";
        }
    }


    // Liste (facultatif)
    @GetMapping
    public String liste(Model model) {
        model.addAttribute("reparations", reparationService.findAll());
        return "liste-reparations";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Reparation reparation = reparationService.findById(id);

        if (reparation.getAppareils() == null) {
            reparation.setAppareils(new ArrayList<>());
        }

        model.addAttribute("reparation", reparation);
        model.addAttribute("reparateurs", reparateurService.findAll()
                .stream()
                .filter(r -> r.getRole() == Role.REPARATEUR)
                .toList());
        return "reparation-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateReparation(@PathVariable Long id, @ModelAttribute("reparation") Reparation reparation) {
        reparation.setId(id); // assure que l'ID est bien conservé
        reparationService.saveWithAppareils(reparation);
        return "redirect:/reparations";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        reparationService.deleteById(id);
        return "redirect:/reparations";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        Reparation reparation = reparationService.findById(id);
        model.addAttribute("reparation", reparation);
        return "reparation-details-view"; // Crée ce fichier HTML
    }

}

