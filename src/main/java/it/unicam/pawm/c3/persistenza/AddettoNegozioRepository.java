package it.unicam.pawm.c3.persistenza;

import it.unicam.pawm.c3.personale.AddettoNegozio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddettoNegozioRepository extends JpaRepository<AddettoNegozio, Long> {
}
