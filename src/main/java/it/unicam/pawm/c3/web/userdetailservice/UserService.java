package it.unicam.pawm.c3.web.userdetailservice;

import it.unicam.pawm.c3.personale.User;
import it.unicam.pawm.c3.web.registration.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDto registrationDto);
}