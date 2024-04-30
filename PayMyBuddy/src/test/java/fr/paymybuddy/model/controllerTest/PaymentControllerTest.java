package fr.paymybuddy.model.controllerTest;

import fr.paymybuddy.controller.PaymentController;
import fr.paymybuddy.model.Payment;
import fr.paymybuddy.model.Transaction;
import fr.paymybuddy.model.User;
import fr.paymybuddy.service.TransactionService;
import fr.paymybuddy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private PaymentController paymentController;

    private Model model;

    @BeforeEach
    void setUp() {
        model = mock(Model.class);
    }

    @Test
    void testShowPaymentForm() {
        User user = new User();
        List<User> friends = new ArrayList<>();
        friends.add(new User());
        friends.add(new User());
        user.setFriends(friends);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("authenticatedUser");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        List<Transaction> transactions = new ArrayList<>();

        when(userService.getUserByEmail("authenticatedUser")).thenReturn(Optional.of(user));

        Page<Transaction> transactionsPage = new PageImpl<>(transactions);
        when(transactionService.getTransactionByReceiverId(0, 0)).thenReturn(transactionsPage);

        String viewName = paymentController.showPaymentForm(model, 0);

        assertEquals("paymentForm", viewName);
        verify(model).addAttribute(eq("payment"), any(Payment.class));
        verify(model).addAttribute("friends", friends);
        verify(model).addAttribute("transactions", transactionsPage);
        verifyNoMoreInteractions(model);
    }

    @Test
    void processPayment_Successful() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@example.com");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
        Payment payment = new Payment();
        payment.setAmount(100.0);
        payment.setRecipientEmail("recipient@example.com");
        payment.setDescription("Test payment");

        when(userService.getUserByEmail(anyString())).thenReturn(java.util.Optional.ofNullable(null));
        when(userService.sendMoneyToOtherUser(100.0, null, null, "Test payment")).thenReturn("Payment successful");

        String result = paymentController.processPayment(payment, model);

        assertEquals("redirect:/payment-success", result);
        verify(model, never()).addAttribute(eq("errorMessage"), anyString());
        assertEquals(payment, paymentController.lastPayment);
    }

    @Test
    void processPayment_Failure() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@example.com");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
        Payment payment = new Payment();
        payment.setAmount(100.0);
        payment.setRecipientEmail("recipient@example.com");
        payment.setDescription("Test payment");

        when(userService.getUserByEmail(anyString())).thenReturn(java.util.Optional.ofNullable(null));
        when(userService.sendMoneyToOtherUser(100.0, null, null, "Test payment")).thenReturn("Recipient not found");

        String result = paymentController.processPayment(payment, model);

        assertEquals("paymentForm", result);
        verify(model, times(1)).addAttribute("errorMessage", "Recipient not found");
        verify(model, times(1)).addAttribute("payment", payment);
        assertEquals(null, paymentController.lastPayment);
    }
}
