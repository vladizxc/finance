package com.finance.FinanceApp.Transaction;

import com.finance.FinanceApp.Category.CategoryType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {
    private final TransactionService transactionService;

    private TransactionResponseDto mapToDto(Transaction t){
        return new TransactionResponseDto(
                t.getId(),
                t.getTitle(),
                t.getAmount(),
                t.getDate(),
                t.getCategory().getName()
        );
    }

    public TransactionsController(TransactionService transactionService){
        if (transactionService == null)
            throw new IllegalArgumentException("TransactionService cannot be null");
        this.transactionService = transactionService;
    }

    @GetMapping
    public List<TransactionResponseDto> getAllTransactions(){
        return transactionService.getAllTransactions()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @GetMapping("/{id}")
    public TransactionResponseDto getTransactionById(@PathVariable Long id) {
        if (id == null || id < 0)
            throw new IllegalArgumentException("Id cannot be null or negative");
        Transaction t = transactionService.getTransactionById(id);
        return mapToDto(t);
    }

    @PostMapping
    public ResponseEntity<String> createTransaction(@RequestBody TransactionDto dto) {
        if(dto == null)
            throw new IllegalArgumentException("DTO cannot be null");
        transactionService.createTransaction(
                dto.getTitle(),
                dto.getAmount(),
                dto.getDate(),
                dto.getCategoryId()
        );
        return ResponseEntity.ok("Transaction created");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id){
        if (id == null || id < 0)
            throw new IllegalArgumentException("Id cannot be null or negative");

        transactionService.deleteTransaction(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTransaction(@PathVariable Long id, @RequestBody TransactionDto dto){
        if (id < 0) {
            throw new IllegalArgumentException("Id cannot be negative");
        }

        if (dto == null) {
            throw new IllegalArgumentException("DTO cannot be null");
        }
        transactionService.updateTransaction(
                id,
                dto.getTitle(),
                dto.getAmount(),
                dto.getDate(),
                dto.getCategoryId()
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/category/{categoryId}")
    public List<TransactionResponseDto> getByCategory(@PathVariable Long categoryId){
        if(categoryId < 0)
            throw new IllegalArgumentException("categoryId cannot be null");
        return transactionService.getTransactionsByCategory(categoryId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @GetMapping("/balance")
    public BigDecimal getBalance(){
        return transactionService.getBalance();
    }

    @GetMapping("/income")
    public BigDecimal getIncome(){
        return transactionService.getTotalIncome();
    }

    @GetMapping("/expense")
    public BigDecimal getExpense(){
        return transactionService.getTotalExpense();
    }

    @GetMapping("/date-range")
    public List<TransactionResponseDto> getByDateRange(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end){
        if (start == null || end == null)
            throw new IllegalArgumentException("Start and end dates cannot be null");
        if (end.isBefore(start))
            throw new IllegalArgumentException("End date cannot be before start date");
        return transactionService.getTransactionsByDateRange(start, end)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @GetMapping("/balance/{categoryId}")
    public BigDecimal getBalanceByCategory(@PathVariable Long categoryId){
        if (categoryId < 0)
            throw new IllegalArgumentException("categoryId cannot be negative");
        return transactionService.getBalanceByCategory(categoryId);
    }

    @GetMapping("/type/{type}")
    public List<TransactionResponseDto> getTransactionsByType(@PathVariable CategoryType type){
        if (type == null)
            throw new IllegalArgumentException("type cannot be null");
        return transactionService.getTransactionsByType(type)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

}
