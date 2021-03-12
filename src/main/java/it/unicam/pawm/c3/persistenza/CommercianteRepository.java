package it.unicam.pawm.c3.persistenza;

import it.unicam.pawm.c3.personale.Commerciante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommercianteRepository extends JpaRepository<Commerciante, Long> {
}
