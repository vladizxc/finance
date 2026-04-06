package com.finance.FinanceApp;

import com.finance.FinanceApp.Transaction.TransactionDto;
import com.finance.FinanceApp.Transaction.TransactionResponseDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionResponseDtoTest {

    @Test
    void ConstructorValidTest(){
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        Long id = 1L;
        TransactionResponseDto t = new TransactionResponseDto(id, "Test", amount, now, "Food");
        assertEquals(1L, t.getId());
        assertEquals("Test", t.getTitle());
        assertEquals(now, t.getDate());
        assertEquals("Food", t.getCategoryName());
    }

    @Test
    void constructorShouldThrowOnInvalidData() {
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100");

        assertThrows(IllegalArgumentException.class,
                () -> new TransactionResponseDto(null,"Test", amount, now, "Food"));

        assertThrows(IllegalArgumentException.class,
                () -> new TransactionResponseDto(-1L,"Test", amount, now, "Food"));

        assertThrows(IllegalArgumentException.class,
                () -> new TransactionResponseDto(1L, "   ", amount, now, "Food"));

        assertThrows(IllegalArgumentException.class,
                () -> new TransactionResponseDto(1L, null, amount, now, "Food"));

        assertThrows(IllegalArgumentException.class,
                () -> new TransactionResponseDto(1L, "Test", null, now, "Food"));

        assertThrows(IllegalArgumentException.class,
                () -> new TransactionResponseDto(1L, "Test", BigDecimal.ZERO, now, "Food"));

        assertThrows(IllegalArgumentException.class,
                () -> new TransactionResponseDto(1L, "Test", amount, null, "Food"));

        assertThrows(IllegalArgumentException.class,
                () -> new TransactionResponseDto(1L, "Test", amount, now, " "));

        assertThrows(IllegalArgumentException.class,
                () -> new TransactionResponseDto(1L, "Test", amount, now, null));
    }

    @Test
    void setIdValid(){
        Long id = 1L;
        Long newId = 2L;
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        TransactionResponseDto t = new TransactionResponseDto(id, "Test", amount, now, "Food");
        t.setId(newId);
        assertEquals(newId, t.getId());
    }

    @Test
    void setIdInvalid(){
        Long id = 1L;
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        TransactionResponseDto t = new TransactionResponseDto(id, "Test", amount, now, "Food");

        assertThrows(IllegalArgumentException.class,
                () -> t.setId(null));
        assertThrows(IllegalArgumentException.class,
                () -> t.setId(-1L));
    }

    @Test
    void setTitleValid(){
        Long id = 1L;
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        TransactionResponseDto t = new TransactionResponseDto(id, "Test", amount, now, "Food");
        t.setTitle("NewTitle");
        assertEquals("NewTitle", t.getTitle());
    }

    @Test
    void setTitleInvalid(){
        Long id = 1L;
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        TransactionResponseDto t = new TransactionResponseDto(id, "Test", amount, now, "Food");

        assertThrows(IllegalArgumentException.class,
                () -> t.setTitle(null));
        assertThrows(IllegalArgumentException.class,
                () -> t.setTitle(" "));
    }

    @Test
    void setAmountValid(){
        Long id = 1L;
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        TransactionResponseDto t = new TransactionResponseDto(id, "Test", amount, now, "Food");

        t.setAmount(new BigDecimal("200"));
        assertEquals(new BigDecimal("200"), t.getAmount());
    }

    @Test
    void setAmountInvalid(){
        Long id = 1L;
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        TransactionResponseDto t = new TransactionResponseDto(id, "Test", amount, now, "Food");

        assertThrows(IllegalArgumentException.class,
                () -> t.setAmount(null));

        assertThrows(IllegalArgumentException.class,
                () -> t.setAmount(BigDecimal.ZERO));
    }

    @Test
    void setDateValid(){
        Long id = 1L;
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        TransactionResponseDto t = new TransactionResponseDto(id, "Test", amount, now, "Food");
        LocalDateTime newNow = LocalDateTime.now().plusHours(2);
        t.setDate(newNow);

        assertEquals(newNow, t.getDate());
    }

    @Test
    void setDateInvalid(){
        Long id = 1L;
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        TransactionResponseDto t = new TransactionResponseDto(id, "Test", amount, now, "Food");

        assertThrows(IllegalArgumentException.class,
                () -> t.setDate(null));
    }

    @Test
    void setCategoryNameValid(){
        Long id = 1L;
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        TransactionResponseDto t = new TransactionResponseDto(id, "Test", amount, now, "Food");
        t.setCategoryName("Test");
        assertEquals("Test", t.getCategoryName());

    }

    @Test
    void setCategoryNameInvalid(){
        Long id = 1L;
        LocalDateTime now = LocalDateTime.now();
        BigDecimal amount = new BigDecimal("100.50");
        TransactionResponseDto t = new TransactionResponseDto(id, "Test", amount, now, "Food");

        assertThrows(IllegalArgumentException.class,
                () -> t.setCategoryName(null));

        assertThrows(IllegalArgumentException.class,
                () -> t.setCategoryName(" "));

    }
}
