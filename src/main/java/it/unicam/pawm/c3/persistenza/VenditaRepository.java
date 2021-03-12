package it.unicam.pawm.c3.persistenza;

import it.unicam.pawm.c3.vendita.Vendita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenditaRepository extends JpaRepository<Vendita, Long> {

    Vendita findTopByOrderByIdDesc();
//        Vendita findByPrezzo(double prezzo);
}
