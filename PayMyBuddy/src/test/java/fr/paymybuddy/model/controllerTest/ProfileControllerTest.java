package fr.paymybuddy.model.controllerTest;

import fr.paymybuddy.controller.ProfileController;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private ProfileController profileController;

    @Test
    void showProfileForm() {
        User user = new User();
        List<User> friends = new ArrayList<>();
        friends.add(new User());
        friends.add(new User());
        user.setFriends(friends);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@example.com");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userService.getUserByEmail("test@example.com")).thenReturn(Optional.of(user));

        String viewName = profileController.showProfileForm(model);

        assertEquals("profile", viewName);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("friends", friends);
    }
}

