package it.unicam.pawm.c3.utils.config;

import it.unicam.pawm.c3.utils.userdetailservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * E' un authentication provider che recupera i dettagli di un utente
     * dallo UserDetailsService
     *
     * @return authentication provider di cui è stato settato lo
     *         userdetails servisce e il password encoder
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    /**
     * Usato dall'implementazione di default dell'authenticationManager()
     * per cercare di ottenere un AuthenticationManager
     *
     * @param auth in cui andremo a specificare l'authentication provider
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * Metodo per andare a sovrascrivere la configurazione HttpSecurity
     *
     * @param http in cui andremo a settare diverse impostazioni
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/registration**", "/static/js/**", "/css/**")
                .permitAll() //diciamo a spring di configurare HttpSecurity solo se il path matcha con quelli indicati
                .anyRequest().authenticated() //diciamo che ogni richiesta deve essere autenticata
                .and()
                .formLogin()
                .loginPage("/login") //specifichiamo di caricare come pagina di login quella presente nel path specificato
                .defaultSuccessUrl("/cliente/") //specifichiamo il path di default che deve essere raggiunto dopo un login con successo
                .permitAll() //da quin in poi(dopo il login) permettiamo tutte le richieste
                .and()
                .logout()
                .invalidateHttpSession(true) //andiamo ad invalidare  la sessione dopo il logut, quindi ne dovrà essere creata una nuova
                .clearAuthentication(true) //puliamo 'autenticazione che magari era rimasta salvata
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // è la requestmatcher che attiva il logut
                .logoutSuccessUrl("/login?logout") //path di default per il logut
                .permitAll();
    }

}
