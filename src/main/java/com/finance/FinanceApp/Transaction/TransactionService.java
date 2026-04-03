package com.finance.FinanceApp.Transaction;

import com.finance.FinanceApp.Category.Category;
import com.finance.FinanceApp.Category.CategoryType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository){
        if(transactionRepository == null) throw new IllegalArgumentException();
        this.transactionRepository = transactionRepository;
    }

    public void createTransaction(String title, BigDecimal amount, LocalDateTime date, Category category){
        if(title == null || title.isBlank()) throw new IllegalArgumentException("Title must have name");
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Amount must be positive");
        if(date == null) throw new NullPointerException("Date cannot be null");
        if (category == null)
            throw new IllegalArgumentException("Category cannot be null");
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
            Category category
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

        if (category != null) {
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
}
