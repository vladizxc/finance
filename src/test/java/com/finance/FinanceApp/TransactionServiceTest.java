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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Test
    void getAllTransactionsTest(){
        TransactionService service = new TransactionService(transactionRepository);
        List<Transaction> allTransactions = service.getAllTransactions();
        verify(transactionRepository).findAll();
    }

    @Test
    void getTransactionByIdShouldReturnTransaction() {
        Long id = 1L;
        Transaction transaction = new Transaction("Test", new BigDecimal("100"), LocalDateTime.now(), new Category());

        when(transactionRepository.findById(id))
                .thenReturn(Optional.of(transaction));

        TransactionService service = new TransactionService(transactionRepository);

        Transaction result = service.getTransactionById(id);

        assertEquals(transaction, result);
    }

    @Test
    void getTransactionByIdShouldThrowWhenNotFound() {
        Long id = 1L;

        when(transactionRepository.findById(id))
                .thenReturn(Optional.empty());

        TransactionService service = new TransactionService(transactionRepository);

        assertThrows(RuntimeException.class,
                () -> service.getTransactionById(id));
    }

    @Test
    void getTransactionByIdShouldThrowOnInvalidId() {
        TransactionService service = new TransactionService(transactionRepository);

        assertThrows(IllegalArgumentException.class,
                () -> service.getTransactionById(-1L));

        assertThrows(IllegalArgumentException.class,
                () -> service.getTransactionById(null));
    }

    @Test
    void deleteTransactionShouldCallDelete() {
        Long id = 1L;
        Transaction transaction = new Transaction("Test", new BigDecimal("100"), LocalDateTime.now(), new Category());

        when(transactionRepository.findById(id))
                .thenReturn(Optional.of(transaction));

        TransactionService service = new TransactionService(transactionRepository);

        service.deleteTransaction(id);

        verify(transactionRepository).delete(transaction);
    }

    @Test
    void deleteTransactionShouldThrowWhenNotFound() {
        Long id = 1L;

        when(transactionRepository.findById(id))
                .thenReturn(Optional.empty());

        TransactionService service = new TransactionService(transactionRepository);

        assertThrows(RuntimeException.class,
                () -> service.deleteTransaction(id));

        verify(transactionRepository, never()).delete(any());
    }

    @Test
    void deleteTransactionShouldThrowOnInvalidId() {
        TransactionService service = new TransactionService(transactionRepository);

        assertThrows(IllegalArgumentException.class,
                () -> service.deleteTransaction(-1L));

        assertThrows(IllegalArgumentException.class,
                () -> service.deleteTransaction(null));
    }

    @Test
    void updateTransactionShouldUpdateOnlyTitle() {
        LocalDateTime now = LocalDateTime.now();
        Category category = new Category();
        Long id = 1L;
        Transaction existing = new Transaction("Old", new BigDecimal("100"), now, category);

        when(transactionRepository.findById(id))
                .thenReturn(Optional.of(existing));

        TransactionService service = new TransactionService(transactionRepository);

        service.updateTransaction(id, "New", null, null, null);

        assertEquals("New", existing.getTitle());
        assertEquals(new BigDecimal("100"), existing.getAmount()); // не изменился

        verify(transactionRepository).save(existing);
    }

    @Test
    void updateTransactionShouldUpdateOnlyAmount() {
        LocalDateTime now = LocalDateTime.now();
        Category category = new Category();
        Long id = 1L;
        Transaction existing = new Transaction("Title", new BigDecimal("100"), now, category);

        when(transactionRepository.findById(id))
                .thenReturn(Optional.of(existing));

        TransactionService service = new TransactionService(transactionRepository);

        service.updateTransaction(id, null, new BigDecimal("110"), null, null);

        assertEquals("Title", existing.getTitle()); // не изменился
        assertEquals(new BigDecimal("110"), existing.getAmount());

        verify(transactionRepository).save(existing);
    }

    @Test
    void updateTransactionShouldUpdateOnlyDate() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime newDate = LocalDateTime.now().minusHours(2);
        Category category = new Category();
        Long id = 1L;
        Transaction existing = new Transaction("Title", new BigDecimal("100"), now, category);

        when(transactionRepository.findById(id))
                .thenReturn(Optional.of(existing));

        TransactionService service = new TransactionService(transactionRepository);

        service.updateTransaction(id, " ", null, newDate, null);

        assertEquals("Title", existing.getTitle()); // не изменился
        assertEquals(newDate, existing.getDate());

        verify(transactionRepository).save(existing);
    }

    @Test
    void updateTransactionShouldUpdateOnlyCategory() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime newDate = LocalDateTime.now().minusHours(2);
        Category category = new Category();
        Category newCategory = new Category();
        Long id = 1L;
        Transaction existing = new Transaction("Title", new BigDecimal("100"), now, category);

        when(transactionRepository.findById(id))
                .thenReturn(Optional.of(existing));

        TransactionService service = new TransactionService(transactionRepository);

        service.updateTransaction(id, null, new BigDecimal("-1"), null, newCategory);

        assertEquals("Title", existing.getTitle()); // не изменился
        assertEquals(new BigDecimal("100"), existing.getAmount()); // не изменился
        assertEquals(newCategory, existing.getCategory());

        verify(transactionRepository).save(existing);
    }

    @Test
    void updateTransactionNotUpdateOnAllNull(){
        LocalDateTime now = LocalDateTime.now();
        Category category = new Category();
        Long id = 1L;
        Transaction existing = new Transaction("Title", new BigDecimal("100"), now, category);

        when(transactionRepository.findById(id))
                .thenReturn(Optional.of(existing));

        TransactionService service = new TransactionService(transactionRepository);

        service.updateTransaction(id, null, null, null, null);

        assertEquals("Title", existing.getTitle()); // не изменился
        assertEquals(new BigDecimal("100"), existing.getAmount());
        assertEquals(now, existing.getDate());
        assertEquals(category, existing.getCategory());

        verify(transactionRepository).save(existing);
    }

    @Test
    void updateTransitionShouldThrowOnInvalidId() {
        TransactionService service = new TransactionService(transactionRepository);

        assertThrows(IllegalArgumentException.class,
                () -> service.updateTransaction(-1L,null, null, null, null));

        assertThrows(IllegalArgumentException.class,
                () -> service.updateTransaction(null,null, null, null, null));
    }

    @Test
    void updateTransactionShouldThrowWhenNotFound() {
        Long id = 1L;

        when(transactionRepository.findById(id))
                .thenReturn(Optional.empty());

        TransactionService service = new TransactionService(transactionRepository);

        assertThrows(RuntimeException.class,
                () -> service.updateTransaction(id, null, null, null, null));

        verify(transactionRepository, never()).save(any());
    }

    @Test
    void getTransactionsByCategoryTest(){
        TransactionService service = new TransactionService(transactionRepository);
        Long categoryId = 1L;
        List<Transaction> allTransactions = service.getTransactionsByCategory(categoryId);
        verify(transactionRepository).findByCategory_Id(categoryId);
    }

    @Test
    void getTransactionsByCategoryThrowsTest(){
        TransactionService service = new TransactionService(transactionRepository);
        assertThrows(IllegalArgumentException.class,
                () -> service.getTransactionsByCategory(null));
        assertThrows(IllegalArgumentException.class,
                () -> service.getTransactionsByCategory(-1L));
    }
}
