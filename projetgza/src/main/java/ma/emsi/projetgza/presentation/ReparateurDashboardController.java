package ma.emsi.projetgza.presentation;

import jakarta.servlet.http.HttpSession;
import ma.emsi.projetgza.dao.models.Reparateur;
import ma.emsi.projetgza.metier.IEmpruntService;
import ma.emsi.projetgza.metier.IrecetteService;
import ma.emsi.projetgza.metier.IReparationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reparateur")
public class ReparateurDashboardController {

    @Autowired
    private IReparationService reparationService;

    @Autowired
    private IEmpruntService empruntService;

    @Autowired
    private IrecetteService recetteService;


    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        Reparateur user = (Reparateur) session.getAttribute("user");
        model.addAttribute("recettes", recetteService.findByReparateurId(user.getId()));
        model.addAttribute("totalRecettes", recetteService.totalByReparateurId(user.getId()));
        model.addAttribute("reparations", reparationService.findByReparateurId(user.getId()));
        model.addAttribute("emprunts", empruntService.findByReparateurId(user.getId()));
        return "dashboard-reparateur";
    }
}

