package fr.paymybuddy;

import fr.paymybuddy.model.Transaction;
import fr.paymybuddy.model.User;
import fr.paymybuddy.service.TransactionService;
import fr.paymybuddy.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PayMyBuddyApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;
	@Autowired
	private TransactionService transactionService;

	public static void main(String[] args) {
		SpringApplication.run(PayMyBuddyApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws  Exception{

		User existingUser = userService.getUserById(4).get();
		System.out.println(existingUser.getPassword());

		existingUser.setPassword("Salut");
		userService.addUser(existingUser);

		existingUser = userService.getUserById(4).get();
		System.out.println(existingUser.getPassword());
	}

}
