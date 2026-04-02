package com.finance.FinanceApp.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranscactionRepository extends JpaRepository<Transaction, Long> {
}
