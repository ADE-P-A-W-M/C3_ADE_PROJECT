package it.unicam.pawm.c3.persistenza;

import it.unicam.pawm.c3.vendita.MerceVendita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerceVenditaRepository extends JpaRepository<MerceVendita, Long> {
}
