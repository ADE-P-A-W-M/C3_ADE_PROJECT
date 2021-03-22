package it.unicam.pawm.c3.repository;

import it.unicam.pawm.c3.model.personale.Ruolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuoloRepository extends JpaRepository<Ruolo, Long>{
}
