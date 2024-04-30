package fr.paymybuddy;


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

		/*User existingUser = userService.getUserByEmail("test@test.com").get();
		List<Transaction> receiveTransactions = transactionService.getTransactionByReceiverId(existingUser.getUserId());
		List<Transaction> sendTransactions = transactionService.getTransactionBySenderId(existingUser.getUserId());


		System.out.println(existingUser.getFirstName() + "(" + existingUser.getEmail() + "):");
		System.out.println("Reçue:");
		receiveTransactions.forEach(transaction -> System.out.println("- " + transaction.getAmount() + " from " + transaction.getSenderId().getFirstName() + "(" + transaction.getSenderId().getEmail() + ")"));
		System.out.println("Envoyer:");
		sendTransactions.forEach(transaction -> System.out.println("- " + transaction.getAmount() + " to " + transaction.getReceiverId().getFirstName() + "(" + transaction.getReceiverId().getEmail() + ")"));


		 */
	}


}
