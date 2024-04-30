package fr.paymybuddy.model.controllerTest;

import fr.paymybuddy.controller.ContactController;
import fr.paymybuddy.model.User;
import fr.paymybuddy.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private ContactController contactController;

    @Test
    void addFriend_UserPresent() {
        User user = new User();
        user.setEmail("test@example.com");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("authenticatedUser");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userService.getUserByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(userService.getUserByEmail("authenticatedUser")).thenReturn(Optional.of(new User()));

        String viewName = contactController.addFriend("test@example.com", model);

        assertEquals("redirect:/contact", viewName);
        verify(userService).addFriend(any(User.class), eq(user));
        verifyNoInteractions(model);
    }

    @Test
    void addFriend_UserNotPresent() {
        when(userService.getUserByEmail("test@example.com")).thenReturn(Optional.empty());

        String viewName = contactController.addFriend("test@example.com", model);

        assertEquals("contact", viewName);
        verify(model).addAttribute("errorMessage", "Utilisateur introuvable");
        verifyNoMoreInteractions(model);
    }
}

