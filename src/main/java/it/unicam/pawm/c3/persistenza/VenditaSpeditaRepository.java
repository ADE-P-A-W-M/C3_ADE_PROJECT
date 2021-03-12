package it.unicam.pawm.c3.persistenza;

import it.unicam.pawm.c3.vendita.VenditaSpedita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenditaSpeditaRepository extends JpaRepository<VenditaSpedita, Long> {


}
