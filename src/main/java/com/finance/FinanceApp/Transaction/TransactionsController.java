package com.finance.FinanceApp.Transaction;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

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
    public List<Transaction> getAllTransactions(){
        return transactionService.getAllTransactions();
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
}
