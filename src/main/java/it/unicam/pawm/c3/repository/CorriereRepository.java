package it.unicam.pawm.c3.repository;

import it.unicam.pawm.c3.model.personale.Corriere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CorriereRepository extends JpaRepository<Corriere, Long> {

    Optional<Corriere> findById(long id);

}
