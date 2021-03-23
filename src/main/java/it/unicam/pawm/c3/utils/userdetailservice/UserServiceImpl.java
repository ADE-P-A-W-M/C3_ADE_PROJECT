package it.unicam.pawm.c3.utils.userdetailservice;

import it.unicam.pawm.c3.model.personale.Cliente;
import it.unicam.pawm.c3.model.personale.Ruolo;
import it.unicam.pawm.c3.model.personale.RuoloSistema;
import it.unicam.pawm.c3.model.personale.User;
import it.unicam.pawm.c3.repository.UserRepository;
import it.unicam.pawm.c3.utils.registration.UserRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserRegistration registration) {
        Optional<User> userToRegister = userRepository.findByEmail(registration.getEmail());
        if(!(userToRegister.isPresent())){
            User user = new User(registration.getNome(), registration.getCognome(), registration.getEmail(), passwordEncoder.encode(registration.getPassword()));
            Cliente cliente = new Cliente(RuoloSistema.CLIENTE);
            user.setRuolo(cliente);
            return userRepository.save(user);
        } else {
            throw new IllegalStateException("email non valida");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if(!user.isPresent()) {
            throw new UsernameNotFoundException("Email o password errati");
        }
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), mapRolesToAuthorities(user.get().getRuolo()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Ruolo> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRuoloSistema().name())).collect(Collectors.toList());
    }

}
