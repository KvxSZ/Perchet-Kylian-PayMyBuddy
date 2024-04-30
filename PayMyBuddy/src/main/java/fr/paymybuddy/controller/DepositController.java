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
public class DepositController {

    @Autowired
    private UserService userService;

    @GetMapping("/deposit")
    public String showDepositForm() {
        return "deposit";
    }

    @PostMapping("/process-deposit")
    public String processDeposit(double amount, Model model) {
        User userAuth = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);
        userService.giveMoney(amount, userAuth);
        return "redirect:/profile";
    }
}
