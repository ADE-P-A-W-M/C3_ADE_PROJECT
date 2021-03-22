package it.unicam.pawm.c3.repository;


import it.unicam.pawm.c3.model.merce.Merce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerceRepository extends JpaRepository<Merce, Long> {
}
