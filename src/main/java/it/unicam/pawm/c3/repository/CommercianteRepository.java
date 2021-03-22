package it.unicam.pawm.c3.repository;

import it.unicam.pawm.c3.model.personale.Commerciante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommercianteRepository extends JpaRepository<Commerciante, Long> {
}
