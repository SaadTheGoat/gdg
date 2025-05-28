package ma.emsi.projetgza.dao.repositories;

import ma.emsi.projetgza.dao.models.Appareil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppareilRepositorie extends JpaRepository<Appareil , Long> {
}
