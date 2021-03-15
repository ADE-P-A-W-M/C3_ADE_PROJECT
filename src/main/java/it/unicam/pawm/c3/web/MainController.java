package it.unicam.pawm.c3.web;

import it.unicam.pawm.c3.personale.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String home(User user) {

        return "index";
    }

}
