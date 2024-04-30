package fr.paymybuddy.model.serviceTest;

import fr.paymybuddy.model.Transaction;
import fr.paymybuddy.model.User;
import fr.paymybuddy.repository.UserRepository;
import fr.paymybuddy.service.TransactionService;
import fr.paymybuddy.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private UserService userService;

    @Test
    void getUsers() {
        User user1 = new User();
        User user2 = new User();
        user1.setUserId(1);
        user2.setUserId(2);

        when(userRepository.findAll()).thenReturn(java.util.Arrays.asList(user1, user2));

        Iterable<User> result = userService.getUsers();

        assertEquals(2, ((java.util.List<User>) result).size());
    }

    @Test
    void getUserById() {
        User user = new User();
        user.setUserId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1);

        assertTrue(result.isPresent());
        assertEquals(user.getUserId(), result.get().getUserId());
    }

    @Test
    void addUser() {
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.addUser(user);

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void takeMoney_EnoughBalance() {
        User user = new User();
        user.setBalance(100.0);

        boolean result = userService.takeMoney(50.0, user);

        assertTrue(result);
        assertEquals(50.0, user.getBalance());
    }

    @Test
    void takeMoney_InsufficientBalance() {
        User user = new User();
        user.setBalance(30.0);

        boolean result = userService.takeMoney(50.0, user);

        assertFalse(result);
        assertEquals(30.0, user.getBalance());
    }

    @Test
    void giveMoney() {
        User user = new User();
        user.setBalance(100.0);

        userService.giveMoney(50.0, user);

        assertEquals(150.0, user.getBalance());
    }

    @Test
    void sendMoneyToOtherUser_Successful() {
        User sender = new User();
        sender.setBalance(100.0);
        User receiver = new User();

        when(transactionService.addTransaction(any(Transaction.class))).thenReturn(new Transaction());

        String result = userService.sendMoneyToOtherUser(50.0, sender, receiver, "Test");

        assertEquals("Payment successful", result);
        assertEquals(47.5, sender.getBalance());
        assertEquals(50.0, receiver.getBalance());
    }

    @Test
    void sendMoneyToOtherUser_InsufficientBalance() {
        User sender = new User();
        sender.setBalance(30.0);
        User receiver = new User();

        String result = userService.sendMoneyToOtherUser(50.0, sender, receiver, "Test");

        assertEquals("Payment failed, Insufficient balance (52.5/30.0)", result);
        assertEquals(30.0, sender.getBalance());
        assertEquals(0.0, receiver.getBalance());
    }
}

