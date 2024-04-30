package fr.paymybuddy.repository;

import fr.paymybuddy.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

    // Find transactions by receiver user ID
    public Page<Transaction> findByReceiverUserId(Integer receiverId, Pageable page);

    // Find transactions by sender user ID
    public Page<Transaction> findBySenderUserId(Integer senderId, Pageable page);
}
