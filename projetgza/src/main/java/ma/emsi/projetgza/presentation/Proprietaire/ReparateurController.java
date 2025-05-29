package ma.emsi.projetgza.presentation.Proprietaire;

import ma.emsi.projetgza.dao.models.Reparateur;
import ma.emsi.projetgza.enums.Role;
import ma.emsi.projetgza.metier.IReparateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reparateurs")
public class ReparateurController {


    @Autowired
    private IReparateurService reparateurService;



    @PostMapping("/add")
    public String addReparateur(@ModelAttribute Reparateur reparateur) {
        reparateur.setRole(Role.REPARATEUR);
        reparateur.setFirstLogin(true);
        reparateurService.save(reparateur);
        return "redirect:/dashboard";
    }
}

