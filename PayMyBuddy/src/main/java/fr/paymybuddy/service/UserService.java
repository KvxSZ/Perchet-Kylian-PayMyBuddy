package fr.paymybuddy.service;

import fr.paymybuddy.model.Transaction;
import fr.paymybuddy.model.User;
import fr.paymybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionService transactionService;

    // Get all users
    public Iterable<User> getUsers(){
        return userRepository.findAll();
    }

    // Get user by ID
    public Optional<User> getUserById(Integer id){
        return userRepository.findById(id);
    }

    // Add a user
    public User addUser(User user){
        return userRepository.save(user);
    }

    // Delete a user by ID
    public void deleteUserById(Integer id){
        userRepository.deleteById(id);
    }

    // Get user by email
    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    // Get friends of a user by ID
    public List<User> getFriendById(Integer id){
        User user = userRepository.findById(id).get();
        return user.getFriends();
    }

    // Add a friend to a user
    public void addFriend(User user, User friend){
        List<User> friendList = user.getFriends();
        friendList.add(friend);
        user.setFriends(friendList);
        userRepository.save(user);
    }

    // Take money from a user's balance
    public boolean takeMoney(double amount, User user){
        if (user.getBalance() >= amount) {
            user.setBalance(user.getBalance() - amount);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    // Add money to a user's balance
    public void giveMoney(double amount, User user){
        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);
    }

    // Send money from one user to another user
    public String sendMoneyToOtherUser(double amount, User sender, User receiver, String description){
        if (takeMoney(amount + (amount * 0.05), sender)){
            giveMoney(amount, receiver);
            transactionService.addTransaction(new Transaction(amount, new Date(), description, sender, receiver));
            return "Payment successful";
        } else {
            return "Payment failed, Insufficient balance " + "(" + (amount + (amount * 0.05)) + "/" + sender.getBalance() + ")";
        }
    }
}
