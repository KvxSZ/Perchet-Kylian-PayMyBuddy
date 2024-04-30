package fr.paymybuddy.model.controllerTest;

import fr.paymybuddy.controller.DepositController;
import fr.paymybuddy.model.User;
import fr.paymybuddy.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepositControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private DepositController depositController;

    @Test
    void showDepositForm() throws Exception {
        String result = depositController.showDepositForm();
        assertEquals("deposit", result);
    }

    @Test
    void processDeposit() throws Exception {
        User user = new User();
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("authenticatedUser");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        user.setBalance(100); // Initial balance
        double amount = 50; // Amount to deposit

        when(userService.getUserByEmail("authenticatedUser")).thenReturn(Optional.of(user));


        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        depositController.processDeposit(amount, null);

        verify(userService, times(1)).giveMoney(amount, user);
    }
}
