package it.unicam.pawm.c3.repository;

import it.unicam.pawm.c3.model.vendita.MerceVendita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerceVenditaRepository extends JpaRepository<MerceVendita, Long> {
}
