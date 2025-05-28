package ma.emsi.projetgza.dao.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appareil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numSerie ;
    private String type ;
    private String marque ;
    private String modele ;
    @NotBlank(message = "La cause ne peut pas être vide")
    private String cause;

    @ManyToOne
    @JoinColumn(name = "reparation_id")
    private Reparation reparation;

}
