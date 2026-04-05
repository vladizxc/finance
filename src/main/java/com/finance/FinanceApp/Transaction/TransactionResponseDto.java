package com.finance.FinanceApp.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponseDto {
    private Long id;
    private String title;
    private BigDecimal amount;
    private LocalDateTime date;
    private String categoryName;

    public TransactionResponseDto(Long id, String title, BigDecimal amount, LocalDateTime date, String categoryName){
        if(id == null || id < 0)
            throw new IllegalArgumentException("Id cannot be null or negative");
        if(title == null || title.isBlank()) throw new IllegalArgumentException("Title must have name");
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Amount must be positive");
        if(date == null) throw new IllegalArgumentException("Date cannot be null");
        if (categoryName == null || categoryName.isBlank())
            throw new IllegalArgumentException("CategoryName cannot be null or blank");
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if(id == null || id < 0)
            throw new IllegalArgumentException("Id cannot be null or negative");
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(title == null || title.isBlank()) throw new IllegalArgumentException("Title must have name");
        this.title = title;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Amount must be positive");
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        if(date == null) throw new IllegalArgumentException("Date cannot be null");
        this.date = date;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        if (categoryName == null || categoryName.isBlank())
            throw new IllegalArgumentException("CategoryName cannot be null or blank");
        this.categoryName = categoryName;
    }
}
