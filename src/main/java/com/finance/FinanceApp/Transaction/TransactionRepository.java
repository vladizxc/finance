package com.finance.FinanceApp.Transaction;

import com.finance.FinanceApp.Category.Category;
import com.finance.FinanceApp.Category.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCategory_Id(Long CategoryId);
    List<Transaction> findByDateBetween(LocalDateTime start, LocalDateTime end);
    List<Transaction> findByCategory_Type(CategoryType type);
}
