package com.finance.FinanceApp;

import com.finance.FinanceApp.Category.Category;
import com.finance.FinanceApp.Transaction.Transaction;
import com.finance.FinanceApp.Transaction.TransactionDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionDtoTest {
    @Test
    void ConstructorValidTest(){
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        Long Id = 1L;
        TransactionDto t = new TransactionDto("Test transaction", amount, now, 1L);

        assertEquals("Test transaction", t.getTitle());
        assertEquals(amount, t.getAmount());
        assertEquals(now, t.getDate());
        assertEquals(1L, t.getCategoryId());
    }

    @Test
    void constructorShouldThrowOnInvalidData() {
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100");
        // title null
        assertThrows(IllegalArgumentException.class,
                () -> new TransactionDto(null, amount, now, 1L));

        // title blank
        assertThrows(IllegalArgumentException.class,
                () -> new TransactionDto("   ", amount, now, 1L));

        // amount null
        assertThrows(IllegalArgumentException.class,
                () -> new TransactionDto("Title", null, now, 1L));

        // amount <= 0
        assertThrows(IllegalArgumentException.class,
                () -> new TransactionDto("Title", BigDecimal.ZERO, now, 1L));

        // date null
        assertThrows(IllegalArgumentException.class,
                () -> new TransactionDto("Title", amount, null, 1L));

        // category null
        assertThrows(IllegalArgumentException.class,
                () -> new TransactionDto("Title", amount, now, null));

        assertThrows(IllegalArgumentException.class,
                () -> new TransactionDto("Title", amount, now, -1L));
    }

    @Test
    void defaultConstructorShouldCreateObject() {
        TransactionDto t = new TransactionDto();

        assertNotNull(t);
    }

    @Test
    void setValidTitle(){
        String title = "Test transaction";
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        TransactionDto t = new TransactionDto(title, amount, now, 1L);
        t.setTitle("NewName");
        assertEquals("NewName", t.getTitle());
    }

    @Test
    void setInvalidTitle(){
        String title = "Test transaction";
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        TransactionDto t = new TransactionDto(title, amount, now, 1L);
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
        TransactionDto t = new TransactionDto(title, amount, now, 1L);
        t.setAmount(new BigDecimal("120.00"));
        assertEquals(new BigDecimal("120.00"), t.getAmount());
    }

    @Test
    void setInvalidAmount(){
        String title = "Test transaction";
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        TransactionDto t = new TransactionDto(title, amount, now, 1L);

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
        TransactionDto t = new TransactionDto(title, amount, now, 1L);
        LocalDateTime newTime = now.minusMinutes(10);
        t.setDate(newTime);
        assertEquals(newTime, t.getDate());
    }

    @Test
    void setInvalidDate(){
        String title = "Test transaction";
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        TransactionDto t = new TransactionDto(title, amount, now, 1L);

        // date null
        assertThrows(IllegalArgumentException.class,
                () -> t.setDate(null));
    }

    @Test
    void setValidCategory(){
        String title = "Test transaction";
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        TransactionDto t = new TransactionDto(title, amount, now, 1L);
        t.setCategoryId(2L);
        assertEquals(2L, t.getCategoryId());
    }

    @Test
    void setInvalidCategory(){
        String title = "Test transaction";
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        TransactionDto t = new TransactionDto(title, amount, now, 1L);

        assertThrows(IllegalArgumentException.class,
                () -> t.setCategoryId(null));

        assertThrows(IllegalArgumentException.class,
                () -> t.setCategoryId(-1L));
    }
}
