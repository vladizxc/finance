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
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.categoryName = categoryName;
    }
}
