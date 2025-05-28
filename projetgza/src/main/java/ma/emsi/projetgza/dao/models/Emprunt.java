package ma.emsi.projetgza.dao.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.emsi.projetgza.enums.Emp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Emprunt {

    @Id
    @GeneratedValue
    private Long id ;

    private float montant ;

    private boolean isemprunt ;


    private String details ;

    @Enumerated(EnumType.STRING)
    private Emp emp = Emp.NON_REGLE ;

    @ManyToOne
    @JoinColumn(name = "reparateur_id", nullable = false)
    private Reparateur reparateur;

}
