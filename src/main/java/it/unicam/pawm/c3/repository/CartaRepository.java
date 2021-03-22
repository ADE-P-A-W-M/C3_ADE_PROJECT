package it.unicam.pawm.c3.repository;

import it.unicam.pawm.c3.model.carta.Carta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaRepository extends JpaRepository<Carta, Long> {
}
