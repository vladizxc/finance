package com.finance.FinanceApp.Transaction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private BigDecimal amount;
    private LocalDateTime date;

    public Transaction(){}

    public Transaction(String title, BigDecimal amount, LocalDateTime date){
        if(title == null || title.isBlank()) throw new IllegalArgumentException("Title must have name");
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Amount must be positive");
        if(date == null) throw new NullPointerException("Date cannot be null");
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

    public void setTitle(String title){
        if(title == null || title.isBlank()) throw new IllegalArgumentException("Title must have name");
        this.title = title;
    }

    public void setAmount(BigDecimal amount){
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Amount must be positive");
        this.amount = amount;
    }

    public void setDate(LocalDateTime date){
        if(date == null) throw new NullPointerException("Date cannot be null");
        this.date = date;
    }
}
