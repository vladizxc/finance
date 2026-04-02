package com.finance.FinanceApp;

import com.finance.FinanceApp.Category.Category;
import com.finance.FinanceApp.Transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTests {

    @Test
    void ConstructorValidTest(){
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        Category c = new Category();
        Transaction t = new Transaction("Test transaction", amount, now, c);

        assertEquals("Test transaction", t.getTitle());
        assertEquals(amount, t.getAmount());
        assertEquals(now, t.getDate());
    }

    @Test
    void constructorShouldThrowOnInvalidData() {
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100");
        Category c = new Category();
        // title null
        assertThrows(IllegalArgumentException.class,
                () -> new Transaction(null, amount, now, c));

        // title blank
        assertThrows(IllegalArgumentException.class,
                () -> new Transaction("   ", amount, now, c));

        // amount null
        assertThrows(IllegalArgumentException.class,
                () -> new Transaction("Title", null, now, c));

        // amount <= 0
        assertThrows(IllegalArgumentException.class,
                () -> new Transaction("Title", BigDecimal.ZERO, now, c));

        // date null
        assertThrows(NullPointerException.class,
                () -> new Transaction("Title", amount, null, c));

        // category null
        assertThrows(IllegalArgumentException.class,
                () -> new Transaction("Title", amount, now, null));
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
        Category c = new Category();
        Transaction t = new Transaction(title, amount, now, c);
        t.setTitle("NewName");
        assertEquals("NewName", t.getTitle());
    }

    @Test
    void setInvalidTitle(){
        String title = "Test transaction";
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        Category c = new Category();
        Transaction t = new Transaction(title, amount, now, c);
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
        Category c = new Category();
        Transaction t = new Transaction(title, amount, now, c);
        t.setAmount(new BigDecimal("120.00"));
        assertEquals(new BigDecimal("120.00"), t.getAmount());
    }

    @Test
    void setInvalidAmount(){
        String title = "Test transaction";
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        Category c = new Category();
        Transaction t = new Transaction(title, amount, now, c);

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
        Category c = new Category();
        Transaction t = new Transaction(title, amount, now, c);
        LocalDateTime newTime = now.minusMinutes(10);
        t.setDate(newTime);
        assertEquals(newTime, t.getDate());
    }

    @Test
    void setInvalidDate(){
        String title = "Test transaction";
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        Category c = new Category();
        Transaction t = new Transaction(title, amount, now, c);

        // date null
        assertThrows(NullPointerException.class,
                () -> t.setDate(null));
    }

    @Test
    void setValidCategory(){
        String title = "Test transaction";
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        Category c = new Category();
        Transaction t = new Transaction(title, amount, now, c);
        Category c1 = new Category();
        t.setCategory(c1);
        assertEquals(c1, t.getCategory());
    }

    @Test
    void setInvalidCategory(){
        String title = "Test transaction";
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        Category c = new Category();
        Transaction t = new Transaction(title, amount, now, c);

        assertThrows(IllegalArgumentException.class,
                () -> t.setCategory(null));

    }
}
