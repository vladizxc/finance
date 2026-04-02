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
        Transaction t = new Transaction("Test transaction", amount, now);

        assertEquals("Test transaction", t.getTitle());
        assertEquals(amount, t.getAmount());
        assertEquals(now, t.getDate());
    }

    @Test
    void constructorShouldThrowOnInvalidData() {
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100");

        // title null
        assertThrows(IllegalArgumentException.class,
                () -> new Transaction(null, amount, now));

        // title blank
        assertThrows(IllegalArgumentException.class,
                () -> new Transaction("   ", amount, now));

        // amount null
        assertThrows(IllegalArgumentException.class,
                () -> new Transaction("Title", null, now));

        // amount <= 0
        assertThrows(IllegalArgumentException.class,
                () -> new Transaction("Title", BigDecimal.ZERO, now));

        // date null
        assertThrows(NullPointerException.class,
                () -> new Transaction("Title", amount, null));
    }

    @Test
    void defaultConstructorShouldCreateObject() {
        Transaction t = new Transaction();

        assertNotNull(t);
    }

    @Test
    void setValidTitle(){
        String title = "Test transaction";
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        Transaction t = new Transaction(title, amount, now);
        t.setTitle("NewName");
        assertEquals("NewName", t.getTitle());
    }

    @Test
    void setInvalidTitle(){
        String title = "Test transaction";
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        Transaction t = new Transaction(title, amount, now);
        // title null
        assertThrows(IllegalArgumentException.class,
                () -> t.setTitle(null));

        // title blank
        assertThrows(IllegalArgumentException.class,
                () -> t.setTitle(" "));
    }

    @Test
    void setValidAmount(){
        String title = "Test transaction";
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        Transaction t = new Transaction(title, amount, now);
        t.setAmount(new BigDecimal("120.00"));
        assertEquals(new BigDecimal("120.00"), t.getAmount());
    }

    @Test
    void setInvalidAmount(){
        String title = "Test transaction";
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        Transaction t = new Transaction(title, amount, now);

        // amount null
        assertThrows(IllegalArgumentException.class,
                () -> t.setAmount(null));

        // amount <= 0
        assertThrows(IllegalArgumentException.class,
                () -> t.setAmount(new BigDecimal("0")));

    }

    @Test
    void setValidDate(){
        String title = "Test transaction";
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        Transaction t = new Transaction(title, amount, now);
        LocalDateTime newTime = now.minusMinutes(10);
        t.setDate(newTime);
        assertEquals(newTime, t.getDate());
    }

    @Test
    void setInvalidDate(){
        String title = "Test transaction";
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        Transaction t = new Transaction(title, amount, now);

        // date null
        assertThrows(NullPointerException.class,
                () -> t.setDate(null));
    }
}
