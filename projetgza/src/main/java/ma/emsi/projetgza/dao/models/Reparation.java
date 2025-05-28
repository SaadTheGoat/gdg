package ma.emsi.projetgza.dao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reparation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codeSuivi;


    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDebut;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateFin;

    @Enumerated(EnumType.STRING)
    private Status status = Status.EN_COURS;

    private double coutTotal;
    private double avance;
    private double reste;
    private boolean recetteCree = false;

    @ManyToOne
    @JoinColumn(name = "reparateur_id", nullable = false)
    private Reparateur reparateur;

    @OneToMany(mappedBy = "reparation", cascade = CascadeType.ALL)
    private List<Appareil> appareils = new ArrayList<>();

    public enum Status {
        EN_COURS,
        TERMINEE,
        CLOTUREE
    }
}
