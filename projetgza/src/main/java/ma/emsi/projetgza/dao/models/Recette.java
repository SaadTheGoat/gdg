package ma.emsi.projetgza.dao.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recette {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double montant ;
    private Date date ;

    @ManyToOne
    @JoinColumn(name = "reparateur_id", nullable = false)
    private Reparateur reparateur;

    @OneToOne
    @JoinColumn(name = "reparation_id", unique = true)
    private Reparation reparation;
}
