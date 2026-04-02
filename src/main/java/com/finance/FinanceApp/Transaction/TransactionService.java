package com.finance.FinanceApp.Transaction;

import com.finance.FinanceApp.Category.Category;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
}
