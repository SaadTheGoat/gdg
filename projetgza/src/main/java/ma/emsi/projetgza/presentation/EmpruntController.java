package ma.emsi.projetgza.presentation;

import jakarta.servlet.http.HttpSession;
import ma.emsi.projetgza.dao.models.Emprunt;
import ma.emsi.projetgza.dao.models.Reparateur;
import ma.emsi.projetgza.enums.Emp;
import ma.emsi.projetgza.metier.IEmpruntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/emprunts")
public class EmpruntController {

    @Autowired
    private IEmpruntService empruntService;

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("emprunt", new Emprunt());
        return "emprunt-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Emprunt emprunt, HttpSession session) {
        Reparateur user = (Reparateur) session.getAttribute("user");

        emprunt.setReparateur(user);

        // ✅ Montant devient négatif pour les remboursements
        if (!emprunt.isIsemprunt()) {
            emprunt.setMontant(-Math.abs(emprunt.getMontant()));
        } else {
            emprunt.setMontant(Math.abs(emprunt.getMontant()));
        }

        empruntService.save(emprunt);
        return "redirect:/reparateur/dashboard";
    }
    @PostMapping("/regler")
    public String reglerEmprunt(@RequestParam Long id, HttpSession session) {
        Emprunt emprunt = empruntService.findById(id).orElseThrow();
        emprunt.setEmp(Emp.REGLE);
        empruntService.save(emprunt);
        return "redirect:/reparateur/dashboard"; // ✅ redirige bien
    }
}

