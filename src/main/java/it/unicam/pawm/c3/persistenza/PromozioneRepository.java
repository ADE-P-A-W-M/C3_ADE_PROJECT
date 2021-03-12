package it.unicam.pawm.c3.persistenza;

import it.unicam.pawm.c3.merce.Promozione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromozioneRepository extends JpaRepository<Promozione, Long> {
}
