package com.finance.FinanceApp;

import com.finance.FinanceApp.Category.Category;
import com.finance.FinanceApp.Category.CategoryRepository;
import com.finance.FinanceApp.Category.CategoryType;
import com.finance.FinanceApp.Transaction.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
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
        // Подготавливаем категорию
        Category category = new Category("Food", CategoryType.EXPENSE);

        // Подготавливаем Transaction
        Transaction t = new Transaction(
                "Test",
                new BigDecimal("100"),
                LocalDateTime.now(),
                category
        );

        Long id = 1L;

        // Мокаем сервис
        when(transactionService.getTransactionById(id)).thenReturn(t);

        // Контроллер
        TransactionsController controller = new TransactionsController(transactionService);

        // Вызываем метод и получаем DTO
        TransactionResponseDto result = controller.getTransactionById(id);

        // Проверяем поля DTO
        assertEquals(t.getTitle(), result.getTitle());
        assertEquals(t.getAmount(), result.getAmount());
        assertEquals(t.getDate(), result.getDate());
        assertEquals(t.getCategory().getName(), result.getCategoryName());
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

    @Test
    void getTransactionByIdShouldReturnDto(){
        Category category = new Category("Food", CategoryType.EXPENSE);
        Transaction t = new Transaction(
                "Test",
                new BigDecimal("100"),
                LocalDateTime.now(),
                category
        );

        when(transactionService.getTransactionById(1L))
                .thenReturn(t);

        TransactionsController controller = new TransactionsController(transactionService);

        TransactionResponseDto result = controller.getTransactionById(1L);

        assertEquals("Test", result.getTitle());
        assertEquals(new BigDecimal("100"), result.getAmount());
        assertEquals(category.getName(), result.getCategoryName());
    }

    @Test
    void getByCategoryInvalidId(){
        assertThrows(IllegalArgumentException.class,
                () -> controller.getByCategory(-1L));
    }

    @Test
    void getByCategoryValid(){
        Category category = new Category("Food", CategoryType.EXPENSE);

        Transaction t1 = new Transaction("Salary Payment", new BigDecimal("1000"), LocalDateTime.now(), category);
        when(transactionService.getTransactionsByCategory(1L)).thenReturn(Arrays.asList(t1));
        assertNotNull(controller.getByCategory(1L));
    }

    @Test
    void getBalanceTest(){
        when(transactionService.getBalance()).thenReturn(BigDecimal.valueOf(100));
        assertEquals(BigDecimal.valueOf(100), controller.getBalance());
    }

    @Test
    void getTotalIncomeTest(){
        when(transactionService.getTotalIncome()).thenReturn(BigDecimal.valueOf(100));
        assertEquals(BigDecimal.valueOf(100), controller.getIncome());
    }

    @Test
    void getTotalExpenseTest(){
        when(transactionService.getTotalExpense()).thenReturn(BigDecimal.valueOf(100));
        assertEquals(BigDecimal.valueOf(100), controller.getExpense());
    }

    @Test
    void getByDateRangeInvalidDate(){
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusHours(10);

        assertThrows(IllegalArgumentException.class,
                () -> controller.getByDateRange(null, end));

        assertThrows(IllegalArgumentException.class,
                () -> controller.getByDateRange(start, null));

        assertThrows(IllegalArgumentException.class,
                () -> controller.getByDateRange(end, start));
    }

    @Test
    void getByDateRangeValidDate(){
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusHours(10);

        assertNotNull(controller.getByDateRange(start, end));
    }

    @Test
    void getBalanceByCategoryInvalid(){
        assertThrows(IllegalArgumentException.class,
                () -> controller.getBalanceByCategory(-1L));
    }

    @Test
    void getBalanceByCategoryTest(){
        when(transactionService.getBalanceByCategory(1L)).thenReturn(BigDecimal.valueOf(100));
        assertEquals(BigDecimal.valueOf(100), controller.getBalanceByCategory(1L));
    }

    @Test
    void getTransactionsByTypeInvalidData(){
        assertThrows(IllegalArgumentException.class,
                () -> controller.getTransactionsByType(null));
    }

    @Test
    void getTransactionsByTypeValidData(){
        Category category = new Category("Food", CategoryType.EXPENSE);

        Transaction t1 = new Transaction("Salary Payment", new BigDecimal("1000"), LocalDateTime.now(), category);
        when(transactionService.getTransactionsByType(CategoryType.EXPENSE)).thenReturn(Arrays.asList(t1));
        assertNotNull(controller.getTransactionsByType(CategoryType.EXPENSE));
    }
}
