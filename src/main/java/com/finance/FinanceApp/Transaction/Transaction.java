package com.finance.FinanceApp.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Transaction {
    private long id;
    private String title;
    private BigDecimal amount;
    private LocalDateTime date;

    public Transaction(){}

    public Transaction(long id, String title, BigDecimal amount, LocalDateTime date){
        if(id < 0) throw new IllegalArgumentException("Id must be positive");
        if(title == null || title.isBlank()) throw new IllegalArgumentException("Title must have name");
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Amount must be positive");
        if(date == null) throw new NullPointerException("Date cannot be null");
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.date = date;
    }

    public long getId(){
        return id;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
