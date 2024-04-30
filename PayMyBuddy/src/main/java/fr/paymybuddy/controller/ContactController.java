package fr.paymybuddy.controller;

import fr.paymybuddy.model.User;
import fr.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {

    @Autowired
    private UserService userService;

    // Display the contact form
    @GetMapping("/contact")
    public String showContactForm() {
        return "contact";
    }

    // Add a friend
    @PostMapping("add-friend")
    public String addFriend(String email, Model model) {
        // Check if the user is present in the database
        boolean userPresent = userService.getUserByEmail(email).isPresent();
        if (userPresent) {
            User user = userService.getUserByEmail(email).get();
            // Get the authenticated user
            User userAuth = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);
            // Add friend relationship
            userService.addFriend(userAuth, user);
            return "redirect:/contact"; // Redirect to the contact page
        } else {
            model.addAttribute("errorMessage", "Utilisateur introuvable"); // Display error message
            return "contact"; // Return to the contact page
        }
    }
}
