package it.unicam.pawm.c3.utils.userdetailservice;

import it.unicam.pawm.c3.model.personale.*;
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

    /**
     * Metodo utilizzato per salvare, registrare un nuovo user. Durante
     * la procedura di salvataggio viene fatto un check per controllare
     * se un utente con la stessa email è gia registrato cosi da prevenire
     * registrazione in eccesso o false. Quando un user i registra gli verrà
     * assegnato in modo automatico il ruolo di cliente.
     *
     * @param registration l'oggetto registrazione i quali campi saranno quelli del futuro user
     * @return il nuovo user
     */
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

    /**
     * Metodo che verrà richiamato durante il login per caricare lo user
     * date email e password.
     *
     * @param email con ci viene caricato lo user e poi fatto il check della password
     * @return userdetails che verrà utilizzato da springsecurity per mantenere l'autenticazione
     * @throws UsernameNotFoundException
     */
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
