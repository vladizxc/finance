package com.finance.FinanceApp;

import com.finance.FinanceApp.Category.Category;
import com.finance.FinanceApp.Transaction.Transaction;
import com.finance.FinanceApp.Transaction.TransactionRepository;
import com.finance.FinanceApp.Transaction.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    TransactionRepository transactionRepository;

    @Test
    void TransactionServiceConstructor(){
        TransactionService service = new TransactionService(transactionRepository);
        assertNotNull(service);
    }

    @Test
    void TransactionServiceInvalidConstructor(){
        assertThrows(IllegalArgumentException.class,
                () -> new TransactionService(null));
    }

    @Test
    void createTransactionValidData(){
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        Category c = new Category();
        TransactionService service = new TransactionService(transactionRepository);

        service.createTransaction("Test", amount, now, c);
        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);

        verify(transactionRepository).save(captor.capture());

        Transaction saved = captor.getValue();

        assertEquals("Test", saved.getTitle());
        assertEquals(amount, saved.getAmount());
        assertEquals(now, saved.getDate());
        assertEquals(c, saved.getCategory());
    }

    @Test
    void createTransactionInvalidData(){
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        Category c = new Category();
        TransactionService service = new TransactionService(transactionRepository);

        //String null
        assertThrows(IllegalArgumentException.class,
                () -> service.createTransaction(null, amount, now, c));

        //String blank
        assertThrows(IllegalArgumentException.class,
                () -> service.createTransaction(" ", amount, now, c));

        //amount null
        assertThrows(IllegalArgumentException.class,
                () -> service.createTransaction("Test", null, now, c));

        //amount < 0
        assertThrows(IllegalArgumentException.class,
                () -> service.createTransaction("Test", new BigDecimal("-1"), now, c));

        //date null
        assertThrows(NullPointerException.class,
                () -> service.createTransaction("Test", amount, null, c));

        //category null
        assertThrows(IllegalArgumentException.class,
                () -> service.createTransaction("Test", amount, now, null));

        verify(transactionRepository, never()).save(any());
    }
}
