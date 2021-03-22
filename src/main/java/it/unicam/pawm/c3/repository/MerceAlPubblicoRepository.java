package it.unicam.pawm.c3.repository;


import it.unicam.pawm.c3.model.merce.Merce;
import it.unicam.pawm.c3.model.merce.MerceAlPubblico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerceAlPubblicoRepository extends JpaRepository<MerceAlPubblico, Long> {

    Optional<MerceAlPubblico> findByMerce(Merce merce);
}
