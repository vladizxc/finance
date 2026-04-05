package com.finance.FinanceApp;

import com.finance.FinanceApp.Category.CategoryRepository;
import com.finance.FinanceApp.Transaction.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionsControllerTest {

    @Mock
    TransactionService transactionService; // ❗ мок сервиса

    TransactionsController controller;

    @BeforeEach
    void init(){
        controller = new TransactionsController(transactionService);
    }

    @Test
    void TransactionsControllerShouldThrowWhenNull(){
        assertThrows(IllegalArgumentException.class,
                () -> new TransactionsController(null));
    }

    @Test
    void TransactionsControllerValid(){
        TransactionsController controller = new TransactionsController(transactionService);
        assertNotNull(controller);
    }

    @Test
    void getAllTransactionsIsEmpty(){
        TransactionsController controller = new TransactionsController(transactionService);
        assertNotNull(controller.getAllTransactions());
    }

    @Test
    void getTransactionByIdInvalidId(){
        TransactionsController controller = new TransactionsController(transactionService);

        assertThrows(IllegalArgumentException.class,
                ()-> controller.getTransactionById(null));

        assertThrows(IllegalArgumentException.class,
                ()-> controller.getTransactionById(-1L));
    }

    @Test
    void getTransactionByIdValid(){
        Transaction t = new Transaction();
        Long id = 1L;

        when(transactionService.getTransactionById(id)).thenReturn(t);

        TransactionsController controller = new TransactionsController(transactionService);
        Transaction result = controller.getTransactionById(id);
        assertEquals(t, result);
    }

    @Test
    void createTransactionValid(){
        TransactionDto dto = new TransactionDto(
                "Test",
                new BigDecimal("100"),
                LocalDateTime.now(),
                1L
        );

        ResponseEntity<String> response = controller.createTransaction(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Transaction created", response.getBody());

        verify(transactionService).createTransaction(
                dto.getTitle(),
                dto.getAmount(),
                dto.getDate(),
                dto.getCategoryId()
        );
    }

    @Test
    void createTransactionInvalid(){
        assertThrows(IllegalArgumentException.class,
                () -> controller.createTransaction(null));
    }

    @Test
    void deleteTransactionValid(){
        ResponseEntity<Void> response = controller.deleteTransaction(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(transactionService).deleteTransaction(1L);
    }

    @Test
    void deleteTransactionInvalidId(){
        assertThrows(IllegalArgumentException.class,
                () -> controller.deleteTransaction(-1L));
        assertThrows(IllegalArgumentException.class,
                () -> controller.deleteTransaction(null));
    }

    @Test
    void updateTransactionInvalidData(){
        assertThrows(IllegalArgumentException.class,
                () -> controller.updateTransaction(1L,null));

        assertThrows(IllegalArgumentException.class,
                () -> controller.updateTransaction(-1L,new TransactionDto()));
    }

    @Test
    void updateTransactionValid(){
        TransactionDto dto = new TransactionDto(
                "Test",
                new BigDecimal("100"),
                LocalDateTime.now(),
                1L
        );

        ResponseEntity<Void> response = controller.updateTransaction(1L,dto);

        assertEquals(200, response.getStatusCodeValue());

        verify(transactionService).updateTransaction(
                1L,
                dto.getTitle(),
                dto.getAmount(),
                dto.getDate(),
                dto.getCategoryId()
        );
    }


}
