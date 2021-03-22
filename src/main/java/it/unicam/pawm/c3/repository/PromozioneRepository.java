package it.unicam.pawm.c3.repository;

import it.unicam.pawm.c3.model.merce.Promozione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromozioneRepository extends JpaRepository<Promozione, Long> {
}
