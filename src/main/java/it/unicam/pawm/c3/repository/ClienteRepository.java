package it.unicam.pawm.c3.repository;

import it.unicam.pawm.c3.model.personale.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

//    Optional<Cliente> findByEmail(String email);

}
