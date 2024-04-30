package fr.paymybuddy.service;

import fr.paymybuddy.model.Transaction;
import fr.paymybuddy.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    // Get all transactions
    public Iterable<Transaction> getTransactions(){
        return transactionRepository.findAll();
    }

    // Get transaction by ID
    public Optional<Transaction> getTransactionById(Integer id){
        return transactionRepository.findById(id);
    }

    // Add a transaction
    public Transaction addTransaction(Transaction transaction){
        return transactionRepository.save(transaction);
    }

    // Delete a transaction by ID
    public void deleteTransactionById(Integer id){
        transactionRepository.deleteById(id);
    }

    // Get transactions by sender ID
    public Page<Transaction> getTransactionBySenderId(Integer senderId, Integer page){
        return transactionRepository.findBySenderUserId(senderId, PageRequest.of(page, 3));
    }

    // Get transactions by receiver ID
    public Page<Transaction> getTransactionByReceiverId(Integer receiverId, Integer page){
        return transactionRepository.findByReceiverUserId(receiverId, PageRequest.of(page, 3));
    }
}
