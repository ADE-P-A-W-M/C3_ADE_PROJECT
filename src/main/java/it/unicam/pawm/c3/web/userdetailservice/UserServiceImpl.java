package it.unicam.pawm.c3.web.userdetailservice;

import it.unicam.pawm.c3.persistenza.NegozioRepository;
import it.unicam.pawm.c3.persistenza.UserRepository;
import it.unicam.pawm.c3.personale.*;
import it.unicam.pawm.c3.web.registration.UserRegistrationDto;
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
    private NegozioRepository negozioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserRegistrationDto registrationDto) {
        User user = new User(registrationDto.getFirstName(),
                registrationDto.getLastName(), registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()));
        Cliente cliente  = new Cliente(RuoloSistema.CLIENTE);
        user.setRuolo(cliente);
        if(user.getEmail().equals("danielepelosi99@gmail.com")){
            AddettoNegozio addettoNegozio = new AddettoNegozio(RuoloSistema.ADDETTONEGOZIO);
            user.setRuolo(addettoNegozio);
            negozioRepository.findAll().get(0).addAddettoNegozio(addettoNegozio);
            Commerciante commerciante = new Commerciante(RuoloSistema.COMMERCIANTE);
            user.setRuolo(commerciante);
        } else if(user.getEmail().equals("edoardoiommi99@gmail.com")){
            Amministratore amministratore = new Amministratore(RuoloSistema.AMMINISTRATORE);
            user.setRuolo(amministratore);
            Corriere corriere = new Corriere(RuoloSistema.CORRIERE, "Bartolini ", "Via della rimembranza", "435436");
            user.setRuolo(corriere);
        } else if(user.getEmail().equals("avdilmehmeti@gmail.com")) {
            Amministratore amministratore = new Amministratore(RuoloSistema.AMMINISTRATORE);
            user.setRuolo(amministratore);
            Corriere corriere = new Corriere(RuoloSistema.CORRIERE, "Ups ", "Via della gioia", "56484");
            user.setRuolo(corriere);
        } else if(user.getEmail().equals("nicoasciutti@gmail.com")) {
            Amministratore amministratore = new Amministratore(RuoloSistema.AMMINISTRATORE);
            user.setRuolo(amministratore);
            Corriere corriere = new Corriere(RuoloSistema.CORRIERE, "SDA ", "Via eremita", "6798646");
            user.setRuolo(corriere);
        }
        else if(user.getEmail().equals("luzilorenzo@gmail.com")) {
            Amministratore amministratore = new Amministratore(RuoloSistema.AMMINISTRATORE);
            user.setRuolo(amministratore);
            Corriere corriere = new Corriere(RuoloSistema.CORRIERE, "GLS ", "Via carlo", "73547");
            user.setRuolo(corriere);
        }
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if(!user.isPresent()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), mapRolesToAuthorities(user.get().getRuolo()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Ruolo> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRuoloSistema().name())).collect(Collectors.toList());
    }

}
