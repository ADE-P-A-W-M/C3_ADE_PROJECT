package it.unicam.pawm.c3.service;

import it.unicam.pawm.c3.personale.Cliente;
import it.unicam.pawm.c3.personale.User;
import it.unicam.pawm.c3.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
}