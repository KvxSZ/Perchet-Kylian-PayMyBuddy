package fr.paymybuddy.controller;

import fr.paymybuddy.model.Payment;
import fr.paymybuddy.model.Transaction;
import fr.paymybuddy.model.User;
import fr.paymybuddy.service.TransactionService;
import fr.paymybuddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PaymentController {

    @Autowired
    UserService userService;

    @Autowired
    TransactionService transactionService;

    // Keep track of the last payment for display purposes
    public Payment lastPayment;

    // Display the payment form
    @GetMapping("/payment-form")
    public String showPaymentForm(Model model, @RequestParam(defaultValue = "0") Integer page) {
        User userAuth = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null);
        List<User> friends = userAuth.getFriends();
        Page<Transaction> transactions = transactionService.getTransactionByReceiverId(userAuth.getUserId(), page);
        model.addAttribute("payment", new Payment());
        model.addAttribute("friends", friends);
        model.addAttribute("transactions", transactions);
        return "paymentForm";
    }

    // Process the payment
    @PostMapping("/process-payment")
    public String processPayment(Payment payment, Model model) {
        // Send money to the recipient
        String str = userService.sendMoneyToOtherUser(payment.getAmount(), userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null), userService.getUserByEmail(payment.getRecipientEmail()).orElse(null), payment.getDescription());
        if (str.equals("Payment successful")) {
            System.out.println("Payment processed: " + payment.toString());
            lastPayment = payment;
            return "redirect:/payment-success"; // Redirect to payment success page
        } else {
            model.addAttribute("errorMessage", str); // Display error message
            model.addAttribute("payment", payment); // Pass payment object back to the form
            return "paymentForm"; // Return to the payment form
        }
    }

    // Display payment success page
    @GetMapping("/payment-success")
    public String showPaymentSuccess(Model model) {
        model.addAttribute("payment", lastPayment); // Pass last payment object to the success page
        return "payment-success";
    }
}
