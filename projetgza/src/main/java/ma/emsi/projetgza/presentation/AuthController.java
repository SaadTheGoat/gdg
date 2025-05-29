package ma.emsi.projetgza.presentation;

import jakarta.servlet.http.HttpSession;
import ma.emsi.projetgza.dao.models.Reparateur;
import ma.emsi.projetgza.enums.Role;
import ma.emsi.projetgza.metier.IReparateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private IReparateurService reparateurService;

    // ✅ Plus besoin de GET "/"
    @GetMapping("/")
    public String redirectRoot() {
        return reparateurService.findProprietaire().isPresent()
                ? "redirect:/login"
                : "redirect:/register";
    }


    // ✅ Register uniquement si le propriétaire n'existe pas encore
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        if (reparateurService.findProprietaire().isPresent()) {
            return "redirect:/login";
        }
        model.addAttribute("reparateur", new Reparateur());
        return "register";
    }

    @PostMapping("/register")
    public String registerProprietaire(@ModelAttribute Reparateur reparateur) {
        reparateur.setRole(Role.PROPRIETAIRE);
        reparateur.setFirstLogin(true);
        reparateurService.save(reparateur);
        return "redirect:/login";
    }

    // ✅ Formulaire de connexion
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("reparateur", new Reparateur());
        return "login";
    }

    // ✅ Connexion avec vérification du rôle
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String mdp,
                        @RequestParam Role role,
                        HttpSession session,
                        Model model) {

        return reparateurService.findByEmail(email)
                .filter(r -> r.getMdp().equals(mdp) && r.getRole() == role)
                .map(user -> {
                    session.setAttribute("user", user);

                    if (user.getRole() == Role.REPARATEUR) {
                        if (user.isFirstLogin()) {
                            return "redirect:/changer-motdepasse";
                        }
                        return "redirect:/reparateur/dashboard";

                    }

                    return "redirect:/dashboard"; // ✅ propriétaire vers dashboard principal
                })
                .orElseGet(() -> {
                    model.addAttribute("error", "Email, mot de passe ou rôle incorrect");
                    return "login";
                });
    }

    @GetMapping("/reparateurs/dashboard")
    public String showReparateurDashboard() {
        return "dashboard-reparateur";
    }
    // ✅ Changement de mot de passe (réparateur - 1ère connexion)
    @GetMapping("/changer-motdepasse")
    public String showChangePasswordForm(Model model) {
        model.addAttribute("reparateur", new Reparateur());
        return "changer-mdp";
    }

    @PostMapping("/changer-motdepasse")
    public String changePassword(@ModelAttribute Reparateur reparateur, HttpSession session) {
        Reparateur user = (Reparateur) session.getAttribute("user");
        user.setMdp(reparateur.getMdp());
        user.setFirstLogin(false);
        reparateurService.save(user);
        session.setAttribute("user", user);
        return "redirect:/reparateurs/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Supprime toutes les données de la session
        return "redirect:/login";
    }


}
