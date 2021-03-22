package it.unicam.pawm.c3.utils.userdetailservice;

import it.unicam.pawm.c3.model.personale.User;
import it.unicam.pawm.c3.utils.registration.UserRegistration;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserRegistration registrationDto);
}