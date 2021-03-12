package it.unicam.pawm.c3.persistenza;


import it.unicam.pawm.c3.merce.MerceAlPubblico;
import it.unicam.pawm.c3.merce.MerceInventarioNegozio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerceInventarioNegozioRepository extends JpaRepository<MerceInventarioNegozio, Long> {

    Optional<MerceInventarioNegozio> findMerceInventarioNegozioByMerceAlPubblico(MerceAlPubblico merce);

}
