package com.finance.FinanceApp;

import com.finance.FinanceApp.Category.Category;
import com.finance.FinanceApp.Category.CategoryRepository;
import com.finance.FinanceApp.Category.CategoryType;
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
import java.util.Arrays;
import java.util.Collections;
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

    @Mock
    CategoryRepository categoryRepository;

    @Test
    void TransactionServiceConstructor(){
        TransactionService service = new TransactionService(transactionRepository, categoryRepository);
        assertNotNull(service);
    }

    @Test
    void TransactionServiceInvalidConstructor(){
        assertThrows(IllegalArgumentException.class,
                () -> new TransactionService(null, categoryRepository));
        assertThrows(IllegalArgumentException.class,
                () -> new TransactionService(transactionRepository, null));
    }

    @Test
    void createTransactionValidData(){
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        TransactionService service = new TransactionService(transactionRepository, categoryRepository);

        Category category = new Category();
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        service.createTransaction("Test", amount, now, 1L);
        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);

        verify(transactionRepository).save(captor.capture());

        Transaction saved = captor.getValue();

        assertEquals("Test", saved.getTitle());
        assertEquals(amount, saved.getAmount());
        assertEquals(now, saved.getDate());
        assertEquals(category, saved.getCategory());
    }

    @Test
    void createTransactionInvalidData(){
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        Category c = new Category();
        TransactionService service = new TransactionService(transactionRepository, categoryRepository);

        //String null
        assertThrows(IllegalArgumentException.class,
                () -> service.createTransaction(null, amount, now, 1L));

        //String blank
        assertThrows(IllegalArgumentException.class,
                () -> service.createTransaction(" ", amount, now, 1L));

        //amount null
        assertThrows(IllegalArgumentException.class,
                () -> service.createTransaction("Test", null, now, 1L));

        //amount < 0
        assertThrows(IllegalArgumentException.class,
                () -> service.createTransaction("Test", new BigDecimal("-1"), now, 1L));

        //date null
        assertThrows(NullPointerException.class,
                () -> service.createTransaction("Test", amount, null, 1L));

        //category null
        assertThrows(IllegalArgumentException.class,
                () -> service.createTransaction("Test", amount, now, null));

        assertThrows(IllegalArgumentException.class,
                () -> service.createTransaction("Test", amount, now, -1L));

        verify(transactionRepository, never()).save(any());
    }

    @Test
    void createTransactionShouldThrowWhenNotFound(){
        Long id = 1L;
        LocalDateTime now = LocalDateTime.now();
        when(categoryRepository.findById(id))
                .thenReturn(Optional.empty());

        TransactionService service = new TransactionService(transactionRepository, categoryRepository);

        assertThrows(RuntimeException.class,
                () -> service.createTransaction("Test", new BigDecimal("10"), now, id));
    }

    @Test
    void getAllTransactionsTest(){
        TransactionService service = new TransactionService(transactionRepository, categoryRepository);
        List<Transaction> allTransactions = service.getAllTransactions();
        verify(transactionRepository).findAll();
    }

    @Test
    void getTransactionByIdShouldReturnTransaction() {
        Long id = 1L;
        Transaction transaction = new Transaction("Test", new BigDecimal("100"), LocalDateTime.now(), new Category());

        when(transactionRepository.findById(id))
                .thenReturn(Optional.of(transaction));

        TransactionService service = new TransactionService(transactionRepository, categoryRepository);

        Transaction result = service.getTransactionById(id);

        assertEquals(transaction, result);
    }

    @Test
    void getTransactionByIdShouldThrowWhenNotFound() {
        Long id = 1L;

        when(transactionRepository.findById(id))
                .thenReturn(Optional.empty());

        TransactionService service = new TransactionService(transactionRepository, categoryRepository);

        assertThrows(RuntimeException.class,
                () -> service.getTransactionById(id));
    }

    @Test
    void getTransactionByIdShouldThrowOnInvalidId() {
        TransactionService service = new TransactionService(transactionRepository, categoryRepository);

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

        TransactionService service = new TransactionService(transactionRepository, categoryRepository);

        service.deleteTransaction(id);

        verify(transactionRepository).delete(transaction);
    }

    @Test
    void deleteTransactionShouldThrowWhenNotFound() {
        Long id = 1L;

        when(transactionRepository.findById(id))
                .thenReturn(Optional.empty());

        TransactionService service = new TransactionService(transactionRepository, categoryRepository);

        assertThrows(RuntimeException.class,
                () -> service.deleteTransaction(id));

        verify(transactionRepository, never()).delete(any());
    }

    @Test
    void deleteTransactionShouldThrowOnInvalidId() {
        TransactionService service = new TransactionService(transactionRepository, categoryRepository);

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

        TransactionService service = new TransactionService(transactionRepository, categoryRepository);

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

        TransactionService service = new TransactionService(transactionRepository, categoryRepository);

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

        TransactionService service = new TransactionService(transactionRepository, categoryRepository);

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

        TransactionService service = new TransactionService(transactionRepository, categoryRepository);
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(newCategory));
        service.updateTransaction(id, null, new BigDecimal("-1"), null, 2L);



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

        TransactionService service = new TransactionService(transactionRepository, categoryRepository);

        service.updateTransaction(id, null, null, null, null);

        assertEquals("Title", existing.getTitle()); // не изменился
        assertEquals(new BigDecimal("100"), existing.getAmount());
        assertEquals(now, existing.getDate());
        assertEquals(category, existing.getCategory());

        verify(transactionRepository).save(existing);
    }

    @Test
    void updateTransitionShouldThrowOnInvalidId() {
        TransactionService service = new TransactionService(transactionRepository, categoryRepository);

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

        TransactionService service = new TransactionService(transactionRepository, categoryRepository);

        assertThrows(RuntimeException.class,
                () -> service.updateTransaction(id, null, null, null, null));

        verify(transactionRepository, never()).save(any());
    }

    @Test
    void updateTransactionShouldThrowWhenCategoryNotFound(){
        LocalDateTime now = LocalDateTime.now();
        Category category = new Category();
        Long id = 1L;
        Transaction existing = new Transaction("Title", new BigDecimal("100"), now, category);

        when(transactionRepository.findById(id))
                .thenReturn(Optional.of(existing));
        Long categoryId = 1L;

        when(transactionRepository.findById(id))
                .thenReturn(Optional.of(existing));

        when(categoryRepository.findById(id))
                .thenReturn(Optional.empty());

        TransactionService service = new TransactionService(transactionRepository, categoryRepository);

        assertThrows(RuntimeException.class,
                () -> service.updateTransaction(id, "Test", new BigDecimal("10"), now, categoryId));
    }

    @Test
    void getTransactionsByCategoryTest(){
        TransactionService service = new TransactionService(transactionRepository, categoryRepository);
        Long categoryId = 1L;
        List<Transaction> allTransactions = service.getTransactionsByCategory(categoryId);
        verify(transactionRepository).findByCategory_Id(categoryId);
    }

    @Test
    void getTransactionsByCategoryThrowsTest(){
        TransactionService service = new TransactionService(transactionRepository, categoryRepository);
        assertThrows(IllegalArgumentException.class,
                () -> service.getTransactionsByCategory(null));
        assertThrows(IllegalArgumentException.class,
                () -> service.getTransactionsByCategory(-1L));
    }

    @Test
    void getBalanceWithIncomeAndExpense(){
        TransactionService service = new TransactionService(transactionRepository, categoryRepository);

        Category incomeCategory = new Category("Salary", CategoryType.INCOME);
        Category expenseCategory = new Category("Food", CategoryType.EXPENSE);


        Transaction t1 = new Transaction("Salary Payment", new BigDecimal("1000"), LocalDateTime.now(), incomeCategory);
        Transaction t2 = new Transaction("Groceries", new BigDecimal("200"), LocalDateTime.now(), expenseCategory);

        when(transactionRepository.findAll()).thenReturn(Arrays.asList(t1, t2));

        BigDecimal balance = service.getBalance();
        assertEquals(new BigDecimal("800"), balance);
    }

    @Test
    void getBalanceOnlyIncome() {
        TransactionService service = new TransactionService(transactionRepository, categoryRepository);

        Category incomeCategory = new Category("Salary", CategoryType.INCOME);
        Transaction t1 = new Transaction("Salary Payment", new BigDecimal("500"), LocalDateTime.now(), incomeCategory);

        when(transactionRepository.findAll()).thenReturn(Collections.singletonList(t1));

        BigDecimal balance = service.getBalance();
        assertEquals(new BigDecimal("500"), balance);
    }

    @Test
    void getBalanceOnlyExpense() {
        TransactionService service = new TransactionService(transactionRepository, categoryRepository);
        Category expenseCategory = new Category("Rent", CategoryType.EXPENSE);
        Transaction t1 = new Transaction("Rent Payment", new BigDecimal("300"), LocalDateTime.now(), expenseCategory);

        when(transactionRepository.findAll()).thenReturn(Collections.singletonList(t1));

        BigDecimal balance = service.getBalance();
        assertEquals(new BigDecimal("-300"), balance);
    }

    @Test
    void getBalanceEmptyList() {
        TransactionService service = new TransactionService(transactionRepository, categoryRepository);

        when(transactionRepository.findAll()).thenReturn(Collections.emptyList());

        BigDecimal balance = service.getBalance();
        assertEquals(BigDecimal.ZERO, balance);
    }

    @Test
    void getTotalIncomeTest(){
        TransactionService service = new TransactionService(transactionRepository, categoryRepository);

        Category incomeCategory = new Category("Salary", CategoryType.INCOME);
        Transaction t1 = new Transaction("Salary Payment", new BigDecimal("500"), LocalDateTime.now(), incomeCategory);

        when(transactionRepository.findAll()).thenReturn(Collections.singletonList(t1));

        BigDecimal income = service.getTotalIncome();
        assertEquals(new BigDecimal("500"), income);
    }

    @Test
    void getTotalIncomeEmptyList() {
        TransactionService service = new TransactionService(transactionRepository, categoryRepository);

        when(transactionRepository.findAll()).thenReturn(Collections.emptyList());

        BigDecimal income = service.getBalance();
        assertEquals(BigDecimal.ZERO, income);
    }

    @Test
    void getTotalExpenseTest() {
        TransactionService service = new TransactionService(transactionRepository, categoryRepository);

        Category expenseCategory = new Category("Rent", CategoryType.EXPENSE);
        Transaction t1 = new Transaction("Rent Payment", new BigDecimal("500"), LocalDateTime.now(), expenseCategory);

        when(transactionRepository.findAll()).thenReturn(Collections.singletonList(t1));

        BigDecimal expense = service.getTotalExpense();
        assertEquals(new BigDecimal("500"), expense);
    }

    @Test
    void getTotalExpenseEmptyList() {
        TransactionService service = new TransactionService(transactionRepository, categoryRepository);

        when(transactionRepository.findAll()).thenReturn(Collections.emptyList());

        BigDecimal expense = service.getBalance();
        assertEquals(BigDecimal.ZERO, expense);
    }

    @Test
    void getBalanceByCategoryTest(){
        TransactionService service = new TransactionService(transactionRepository, categoryRepository);
        // Мокаем категорию
        Category incomeCategory = mock(Category.class);
        when(incomeCategory.getType()).thenReturn(CategoryType.INCOME);
        //when(incomeCategory.getId()).thenReturn(1L);

        Category expenseCategory = mock(Category.class);
        when(expenseCategory.getType()).thenReturn(CategoryType.EXPENSE);
        //when(expenseCategory.getId()).thenReturn(1L);

        Transaction t1 = new Transaction("Salary", new BigDecimal("1000"), LocalDateTime.now(), incomeCategory);
        Transaction t2 = new Transaction("Lunch", new BigDecimal("200"), LocalDateTime.now(), expenseCategory);

        when(transactionRepository.findByCategory_Id(1L)).thenReturn(Arrays.asList(t1, t2));

        BigDecimal balance = service.getBalanceByCategory(1L);
        assertEquals(new BigDecimal("800"), balance);
    }

    @Test
    void getBalanceByCategoryOnlyIncome(){
        TransactionService service = new TransactionService(transactionRepository, categoryRepository);
        // Мокаем категорию
        Category incomeCategory = mock(Category.class);
        when(incomeCategory.getType()).thenReturn(CategoryType.INCOME);

        Transaction t1 = new Transaction("Salary", new BigDecimal("1000"), LocalDateTime.now(), incomeCategory);

        when(transactionRepository.findByCategory_Id(1L)).thenReturn(Collections.singletonList(t1));
        BigDecimal balance = service.getBalanceByCategory(1L);
        assertEquals(new BigDecimal("1000"), balance);

    }

    @Test
    void getBalanceByCategoryOnlyExpense(){
        TransactionService service = new TransactionService(transactionRepository, categoryRepository);

        Category expenseCategory = mock(Category.class);
        when(expenseCategory.getType()).thenReturn(CategoryType.EXPENSE);

        Transaction t2 = new Transaction("Lunch", new BigDecimal("200"), LocalDateTime.now(), expenseCategory);

        when(transactionRepository.findByCategory_Id(1L)).thenReturn(Collections.singletonList(t2));

        BigDecimal balance = service.getBalanceByCategory(1L);
        assertEquals(new BigDecimal("-200"), balance);
    }

    @Test
    void getBalanceByCategoryEmptyList(){
        TransactionService service = new TransactionService(transactionRepository, categoryRepository);

        lenient().when(transactionRepository.findByCategory_Id(1L)).thenReturn(Collections.emptyList());

        BigDecimal balance = service.getBalance();
        assertEquals(BigDecimal.ZERO, balance);
    }

    @Test
    void getTransactionsByDateRangeInvalidStartOrEnd(){
        TransactionService service = new TransactionService(transactionRepository, categoryRepository);
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusHours(2);

        assertThrows(IllegalArgumentException.class,
                () -> service.getTransactionsByDateRange(null,end));

        assertThrows(IllegalArgumentException.class,
                () -> service.getTransactionsByDateRange(start, null));

        assertThrows(IllegalArgumentException.class,
                () -> service.getTransactionsByDateRange(end, start));
    }

    @Test
    void getTransactionsByDateRangeTest() {
        TransactionService service = new TransactionService(transactionRepository, categoryRepository);
        LocalDateTime start = LocalDateTime.of(2026, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2026, 1, 31, 23, 59);

        service.getTransactionsByDateRange(start, end);

        verify(transactionRepository).findByDateBetween(start, end);
    }

    @Test
    void getTransactionsByTypeTest() {
        TransactionService service = new TransactionService(transactionRepository, categoryRepository);
        CategoryType type = CategoryType.INCOME;

        service.getTransactionsByType(type);

        verify(transactionRepository).findByCategory_Type(type);
    }

    @Test
    void getTransactionsByTypeThrowsTest() {
        TransactionService service = new TransactionService(transactionRepository, categoryRepository);

        assertThrows(IllegalArgumentException.class,
                () -> service.getTransactionsByType(null));
    }
}
