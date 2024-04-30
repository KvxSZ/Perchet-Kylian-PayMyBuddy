package fr.paymybuddy.model.serviceTest;

import fr.paymybuddy.model.Transaction;
import fr.paymybuddy.repository.TransactionRepository;
import fr.paymybuddy.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void getTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction());
        transactions.add(new Transaction());

        when(transactionRepository.findAll()).thenReturn(transactions);

        Iterable<Transaction> result = transactionService.getTransactions();

        assertEquals(transactions, result);
    }

    @Test
    void getTransactionById() {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(1);

        when(transactionRepository.findById(1)).thenReturn(Optional.of(transaction));

        Optional<Transaction> result = transactionService.getTransactionById(1);

        assertEquals(Optional.of(transaction), result);
    }

    @Test
    void addTransaction() {
        Transaction transaction = new Transaction();
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        Transaction result = transactionService.addTransaction(transaction);

        assertEquals(transaction, result);
    }

    @Test
    void deleteTransactionById() {
        doNothing().when(transactionRepository).deleteById(1);

        transactionService.deleteTransactionById(1);

        verify(transactionRepository, times(1)).deleteById(1);
    }

    @Test
    void getTransactionBySenderId() {
        List<Transaction> transactions = new ArrayList<>();
        Page<Transaction> transactionsPage = new PageImpl<>(transactions);

        when(transactionRepository.findBySenderUserId(1, PageRequest.of(0, 3))).thenReturn(transactionsPage);

        Iterable<Transaction> result = transactionService.getTransactionBySenderId(1, 0);

        assertEquals(transactionsPage, result);
    }

    @Test
    void getTransactionByReceiverId() {
        List<Transaction> transactions = new ArrayList<>();
        Page<Transaction> transactionsPage = new PageImpl<>(transactions);

        when(transactionRepository.findByReceiverUserId(1, PageRequest.of(0, 3))).thenReturn(transactionsPage);

        Iterable<Transaction> result = transactionService.getTransactionByReceiverId(1, 0);

        assertEquals(transactionsPage, result);
    }
}

