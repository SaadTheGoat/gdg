package ma.emsi.projetgza.dao.repositories;

import ma.emsi.projetgza.dao.models.Reparateur;
import ma.emsi.projetgza.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReparateurRepositorie extends JpaRepository<Reparateur , Long> {

}
