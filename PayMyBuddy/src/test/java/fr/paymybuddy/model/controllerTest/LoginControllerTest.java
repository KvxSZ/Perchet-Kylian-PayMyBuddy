package fr.paymybuddy.model.controllerTest;

import fr.paymybuddy.controller.LoginController;
import fr.paymybuddy.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private LoginController loginController;

    @Test
    void showLoginForm() {
        String result = loginController.showLoginForm();
        assertEquals("login", result);
    }

    @Test
    void home() {
        String result = loginController.home();
        assertEquals("home", result);
    }
}