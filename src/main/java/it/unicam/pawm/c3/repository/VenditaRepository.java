package it.unicam.pawm.c3.repository;

import it.unicam.pawm.c3.model.vendita.Vendita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenditaRepository extends JpaRepository<Vendita, Long> {

    Vendita findTopByOrderByIdDesc();
//        Vendita findByPrezzo(double prezzo);
}
