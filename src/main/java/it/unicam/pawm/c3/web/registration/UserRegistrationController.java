package it.unicam.pawm.c3.web.registration;

import it.unicam.pawm.c3.web.userdetailservice.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    private UserService userService;

    public UserRegistrationController(UserService userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegistration userRegistrationDto() {
        return new UserRegistration();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping()
    public String registerUserAccount(@ModelAttribute("user") UserRegistration registrationDto, Model model) {
        try{
            userService.save(registrationDto);
            return "redirect:/registration?success";
        } catch (IllegalStateException e){
            model.addAttribute("alertEmailNonValida", "Registrazione incompleta, email non valida");
            return "registration";
        }
    }
}