package it.unicam.pawm.c3.persistenza;


import it.unicam.pawm.c3.merce.Merce;
import it.unicam.pawm.c3.merce.MerceAlPubblico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerceAlPubblicoRepository extends JpaRepository<MerceAlPubblico, Long> {

    Optional<MerceAlPubblico> findByMerce(Merce merce);
}
