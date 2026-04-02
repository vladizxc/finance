package com.finance.FinanceApp;

import com.finance.FinanceApp.Transaction.Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTests {

    @Test
    void ConstructorValidTest(){
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        Transaction t = new Transaction(1, "Test transaction", amount, now);

        assertEquals(1, t.getId());
        assertEquals("Test transaction", t.getTitle());
        assertEquals(amount, t.getAmount());
        assertEquals(now, t.getDate());
    }

    @Test
    void constructorShouldThrowOnInvalidData() {
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100");

        // id < 0
        assertThrows(IllegalArgumentException.class,
                () -> new Transaction(-1, "Title", amount, now));

        // title null
        assertThrows(IllegalArgumentException.class,
                () -> new Transaction(1, null, amount, now));

        // title blank
        assertThrows(IllegalArgumentException.class,
                () -> new Transaction(1, "   ", amount, now));

        // amount null
        assertThrows(IllegalArgumentException.class,
                () -> new Transaction(1, "Title", null, now));

        // amount <= 0
        assertThrows(IllegalArgumentException.class,
                () -> new Transaction(1, "Title", BigDecimal.ZERO, now));

        // date null
        assertThrows(NullPointerException.class,
                () -> new Transaction(1, "Title", amount, null));
    }

    @Test
    void defaultConstructorShouldCreateObject() {
        Transaction t = new Transaction();

        assertNotNull(t);
    }
}
