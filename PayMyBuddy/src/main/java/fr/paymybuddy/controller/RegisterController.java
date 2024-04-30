package fr.paymybuddy.controller;

import fr.paymybuddy.configuration.SpringSecurityConfiguration;
import fr.paymybuddy.model.User;
import fr.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    @Autowired
    UserService userService;

    @Autowired
    SpringSecurityConfiguration springSecurityConfiguration;

    // Display the registration form
    @GetMapping("/register")
    public String showInscriptionForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Process the registration form submission
    @PostMapping("/register")
    public String submitInscriptionForm(User user, Model model) {
        // Check if the email already exists
        if(userService.getUserByEmail(user.getEmail()).isPresent()){
            model.addAttribute("emailExists", true); // Display error message if email already exists
            return "register"; // Return to the registration form
        }
        // Encode the password using Spring Security password encoder
        String pswdSecu = springSecurityConfiguration.passwordEncoder().encode(user.getPassword());
        user.setPassword(pswdSecu);
        userService.addUser(user); // Add user to the database
        return "redirect:/login"; // Redirect to login page after successful registration
    }
}
