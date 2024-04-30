package fr.paymybuddy.model.controllerTest;

import fr.paymybuddy.configuration.SpringSecurityConfiguration;
import fr.paymybuddy.controller.RegisterController;
import fr.paymybuddy.model.User;
import fr.paymybuddy.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private SpringSecurityConfiguration springSecurityConfiguration;

    @InjectMocks
    private RegisterController registerController;

    @Mock
    private Model model;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void showInscriptionForm() {
        String result = registerController.showInscriptionForm(model);
        assertEquals("register", result);
    }

    @Test
    void submitInscriptionForm_UserExists() {
        User user = new User();
        user.setEmail("existing@example.com");

        when(userService.getUserByEmail("existing@example.com")).thenReturn(Optional.of(new User()));

        String viewName = registerController.submitInscriptionForm(user, model);

        assertEquals("register", viewName);
        verify(model).addAttribute("emailExists", true);
        verify(userService, never()).addUser(user);
    }

    @Test
    void submitInscriptionForm_Success() {
        User user = new User();
        user.setEmail("new@example.com");
        user.setPassword("password");

        when(userService.getUserByEmail("new@example.com")).thenReturn(Optional.empty());
        when(springSecurityConfiguration.passwordEncoder()).thenReturn(passwordEncoder);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        String viewName = registerController.submitInscriptionForm(user, model);

        assertEquals("redirect:/login", viewName);
        verify(userService).addUser(user);
    }
}
