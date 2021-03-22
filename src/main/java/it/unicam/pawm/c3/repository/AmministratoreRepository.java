package it.unicam.pawm.c3.repository;

import it.unicam.pawm.c3.model.personale.Amministratore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmministratoreRepository extends JpaRepository<Amministratore, Long> {
}
