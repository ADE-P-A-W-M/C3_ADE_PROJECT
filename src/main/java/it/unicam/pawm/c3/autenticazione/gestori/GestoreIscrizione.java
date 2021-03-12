package it.unicam.pawm.c3.autenticazione.gestori;

import it.unicam.pawm.c3.persistenza.ClienteRepository;
import it.unicam.pawm.c3.personale.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GestoreIscrizione {

    private final ClienteRepository clienteRepository;

    @Autowired
    public GestoreIscrizione(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void iscrizione(String nome,String cognome,String email,String password){
//        Cliente cliente = new Cliente(nome,cognome,email,password);
//        clienteRepository.save(cliente);
    }
}
