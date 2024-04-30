package fr.paymybuddy.controller;

import fr.paymybuddy.model.User;
import fr.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    UserService userService;

    // Display the profile page
    @GetMapping("/profile")
    public String showProfileForm(Model model) {
        User userAuth = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);
        List<User> friends = userAuth.getFriends();
        model.addAttribute("user", userAuth);
        model.addAttribute("friends", friends);
        return "profile";
    }
}
