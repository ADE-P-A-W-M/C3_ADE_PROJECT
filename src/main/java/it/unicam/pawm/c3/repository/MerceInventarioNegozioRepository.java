package it.unicam.pawm.c3.repository;


import it.unicam.pawm.c3.model.merce.MerceAlPubblico;
import it.unicam.pawm.c3.model.merce.MerceInventarioNegozio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerceInventarioNegozioRepository extends JpaRepository<MerceInventarioNegozio, Long> {

    Optional<MerceInventarioNegozio> findMerceInventarioNegozioByMerceAlPubblico(MerceAlPubblico merce);

}
