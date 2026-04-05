package com.finance.FinanceApp.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDto {

    private String title;
    private BigDecimal amount;
    private LocalDateTime date;
    private Long categoryId;

    public TransactionDto() {}

    public TransactionDto(String title, BigDecimal amount, LocalDateTime date, Long categoryId) {
            if (title == null || title.isBlank())
                throw new IllegalArgumentException("Title cannot be null or blank");
            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
                throw new IllegalArgumentException("Amount cannot be null");
            if (date == null)
                throw new IllegalArgumentException("Date cannot be null");
            if (categoryId == null || categoryId < 0)
                throw new IllegalArgumentException("CategoryID cannot be null or negative");
            this.title = title;
            this.amount = amount;
            this.date = date;
            this.categoryId = categoryId;
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

        public Long getCategoryId() {
            return categoryId;
        }

        public void setTitle(String title) {
            if (title == null || title.isBlank())
                throw new IllegalArgumentException("Title cannot be null or blank");
            this.title = title;
        }

        public void setAmount(BigDecimal amount) {
            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
                throw new IllegalArgumentException("Amount cannot be null");
            this.amount = amount;
        }

        public void setDate(LocalDateTime date) {
            if (date == null)
                throw new IllegalArgumentException("Date cannot be null");
            this.date = date;
        }

        public void setCategoryId(Long categoryId) {
            if (categoryId == null || categoryId < 0)
                throw new IllegalArgumentException("CategoryID cannot be null or negative");
            this.categoryId = categoryId;
        }
}

