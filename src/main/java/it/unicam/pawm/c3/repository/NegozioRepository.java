package it.unicam.pawm.c3.repository;

import it.unicam.pawm.c3.model.Negozio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NegozioRepository extends JpaRepository<Negozio, Long> {
}
