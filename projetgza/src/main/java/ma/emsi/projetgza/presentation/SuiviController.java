package ma.emsi.projetgza.presentation;

import ma.emsi.projetgza.dao.models.Reparation;
import ma.emsi.projetgza.metier.IReparationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class SuiviController {

    @Autowired
    private IReparationService reparationService;

    @GetMapping("/suivi")
    public String showForm() {
        return "suivi";
    }

    @PostMapping("/suivi")
    public String searchReparation(@RequestParam String codeSuivi, Model model) {
        if (codeSuivi == null || codeSuivi.isBlank()) {
            model.addAttribute("error", "Le code de suivi est requis.");
            return "suivi";
        }

        Optional<Reparation> reparationOpt = reparationService.getReparationByCodeSuivi(codeSuivi);

        if (reparationOpt.isPresent()) {
            model.addAttribute("reparation", reparationOpt.get());
            return "suivi-resultat";
        } else {
            model.addAttribute("error", "Aucune réparation trouvée avec ce code.");
            return "suivi";
        }
    }
}
