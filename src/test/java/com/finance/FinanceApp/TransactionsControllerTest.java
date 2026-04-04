package com.finance.FinanceApp;

import com.finance.FinanceApp.Transaction.Transaction;
import com.finance.FinanceApp.Transaction.TransactionRepository;
import com.finance.FinanceApp.Transaction.TransactionService;
import com.finance.FinanceApp.Transaction.TransactionsController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionsControllerTest {

    @Mock
    TransactionRepository transactionRepository;

    TransactionService service;

    @BeforeEach
    void init(){
        service = new TransactionService(transactionRepository);
    }

    @Test
    void TransactionsControllerShouldThrowWhenNull(){
        assertThrows(IllegalArgumentException.class,
                () -> new TransactionsController(null));
    }

    @Test
    void TransactionsControllerValid(){
        TransactionsController controller = new TransactionsController(service);
        assertNotNull(controller);
    }

    @Test
    void getAllTransactionsIsEmpty(){
        TransactionsController controller = new TransactionsController(service);
        assertNotNull(controller.getAllTransactions());
    }

    @Test
    void getTransactionByIdInvalidId(){
        TransactionsController controller = new TransactionsController(service);

        assertThrows(IllegalArgumentException.class,
                ()-> controller.getTransactionById(null));

        assertThrows(IllegalArgumentException.class,
                ()-> controller.getTransactionById(-1L));
    }

    @Test
    void getTransactionByIdValid(){
        Transaction t = new Transaction();
        Long id = 1L;

        when(transactionRepository.findById(id)).thenReturn(Optional.of(t));

        TransactionsController controller = new TransactionsController(service);
        Transaction result = controller.getTransactionById(id);
        assertEquals(t, result);
    }


}
