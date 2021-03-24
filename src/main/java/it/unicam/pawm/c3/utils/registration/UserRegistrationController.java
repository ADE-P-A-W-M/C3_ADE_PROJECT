package it.unicam.pawm.c3.utils.registration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    private UserService userService;

    public UserRegistrationController(UserService userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegistration userRegistration() {
        return new UserRegistration();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping()
    public String registerUser(@ModelAttribute("user") UserRegistration registration, Model model) {
        try{
            userService.save(registration);
            return "redirect:/registration?success";
        } catch (IllegalStateException e){
            model.addAttribute("alertEmailNonValida", "Registrazione incompleta, email non valida");
            return "registration";
        }
    }
}