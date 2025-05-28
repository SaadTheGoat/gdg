package ma.emsi.projetgza.dao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import ma.emsi.projetgza.enums.Role;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reparateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom ne peut pas être vide")
    private String nom;

    @NotBlank(message = "Le prénom ne peut pas être vide")
    private String prenom;

    @Email(message = "L'email doit être valide")
    @NotBlank(message = "L'email est requis")
    private String email;

    @NotBlank(message = "Le mot de passe est requis")
    private String mdp;

    private Float commision ;

    @Column(nullable = false)
    private boolean firstLogin = true;

    @Enumerated(EnumType.STRING)
    private Role role = Role.PROPRIETAIRE ;

    @OneToMany(mappedBy = "reparateur", cascade = CascadeType.ALL)
    private List<Reparation> reparations = new ArrayList<>();

    @OneToMany(mappedBy = "reparateur", cascade = CascadeType.ALL)
    private List<Emprunt> emprunts = new ArrayList<>();

    @OneToMany(mappedBy = "reparateur", cascade = CascadeType.ALL)
    private List<Recette> recettes = new ArrayList<>();

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

