package com.finance.FinanceApp.Transaction;

import com.finance.FinanceApp.Category.Category;
import com.finance.FinanceApp.Category.CategoryRepository;
import com.finance.FinanceApp.Category.CategoryType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    public TransactionService(TransactionRepository transactionRepository, CategoryRepository categoryRepository){
        if(transactionRepository == null) throw new IllegalArgumentException();
        if(categoryRepository == null) throw new IllegalArgumentException();
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }

    public void createTransaction(String title, BigDecimal amount, LocalDateTime date, Long id){
        if(title == null || title.isBlank()) throw new IllegalArgumentException("Title must have name");
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Amount must be positive");
        if(date == null) throw new NullPointerException("Date cannot be null");
        if (id == null || id < 0)
            throw new IllegalArgumentException("CategoryId cannot be null or negative");
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Transaction transaction = new Transaction(title, amount, date, category);
        transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions(){
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(Long id){
        if (id == null || id < 0)
            throw new IllegalArgumentException("Id cannot be negative");

        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public void deleteTransaction(Long id){
        if (id == null || id < 0)
            throw new IllegalArgumentException("Id cannot be negative");

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        transactionRepository.delete(transaction);
    }

    public Transaction updateTransaction(
            Long id,
            String title,
            BigDecimal amount,
            LocalDateTime date,
            Long categoryId
    ) {
        if (id == null || id < 0)
            throw new IllegalArgumentException("Invalid id");

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (title != null && !title.isBlank()) {
            transaction.setTitle(title);
        }

        if (amount != null && amount.compareTo(BigDecimal.ZERO) > 0) {
            transaction.setAmount(amount);
        }

        if (date != null) {
            transaction.setDate(date);
        }

        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(()-> new RuntimeException("Category not found"));
            transaction.setCategory(category);
        }

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsByCategory(Long categoryId){
        if(categoryId == null || categoryId < 0) throw new IllegalArgumentException();
        return transactionRepository.findByCategory_Id(categoryId);
    }

    public BigDecimal getBalance() {
        BigDecimal balance = BigDecimal.ZERO;

        List<Transaction> transactions = this.getAllTransactions();

        for (Transaction t : transactions) {

            CategoryType type = t.getCategory().getType();

            if (type == CategoryType.INCOME) {
                balance = balance.add(t.getAmount());
            } else {
                balance = balance.subtract(t.getAmount());
            }
        }

        return balance;
    }

    public BigDecimal getTotalIncome(){
        BigDecimal income = BigDecimal.ZERO;

        List<Transaction> transactions = this.getAllTransactions();
        for(Transaction t : transactions){
            CategoryType type = t.getCategory().getType();
            if(type == CategoryType.INCOME){
                income = income.add(t.getAmount());
            }
        }
        return income;
    }

    public BigDecimal getTotalExpense(){
        BigDecimal expense = BigDecimal.ZERO;

        List<Transaction> transactions = this.getAllTransactions();
        for(Transaction t : transactions){
            CategoryType type = t.getCategory().getType();
            if(type == CategoryType.EXPENSE){
                expense = expense.add(t.getAmount());
            }
        }
        return expense;
    }

    public BigDecimal getBalanceByCategory(Long categoryId){
        BigDecimal balance = BigDecimal.ZERO;

        List<Transaction> transactions = this.getTransactionsByCategory(categoryId);
        for(Transaction t : transactions){
            CategoryType type = t.getCategory().getType();
            if (type == CategoryType.INCOME) {
                balance = balance.add(t.getAmount());
            } else {
                balance = balance.subtract(t.getAmount());
            }
        }
        return balance;
    }

    public List<Transaction> getTransactionsByDateRange(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null)
            throw new IllegalArgumentException("Start and end dates cannot be null");
        if (end.isBefore(start))
            throw new IllegalArgumentException("End date cannot be before start date");

        return transactionRepository.findByDateBetween(start, end);
    }

    public List<Transaction> getTransactionsByType(CategoryType type){
        if (type == null)
            throw new IllegalArgumentException("Type cannot be null");
        return transactionRepository.findByCategory_Type(type);
    }
}
