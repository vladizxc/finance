package com.finance.FinanceApp;

import com.finance.FinanceApp.Category.Category;
import com.finance.FinanceApp.Category.CategoryType;
import com.finance.FinanceApp.Transaction.Transaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CategoryTests {
    @Test
    void CategoryEmptyConstructor(){
        Category c = new Category();
        assertNotNull(c);
    }

    @Test
    void CategoryValidConstructor(){
        String name = "TestName";
        CategoryType type = CategoryType.EXPENSE;
        Category c = new Category(name, type);

        assertEquals(name, c.getName());
        assertEquals(type, c.getType());
    }

    @Test
    void constructorShouldThrowOnInvalidData(){
        String name = "TestName";
        CategoryType type = CategoryType.EXPENSE;

        // name null
        assertThrows(IllegalArgumentException.class,
                () -> new Category(null, type));

        // name blank
        assertThrows(IllegalArgumentException.class,
                () -> new Category(" ", type));

        assertThrows(NullPointerException.class,
                () -> new Category(name, null));
    }

    @Test
    void setNameValidTest(){
        String name = "TestName";
        CategoryType type = CategoryType.EXPENSE;
        Category c = new Category(name, type);
        c.setName("NewName");
        assertEquals("NewName", c.getName());
    }

    @Test
    void setInvalidName(){
        String name = "TestName";
        CategoryType type = CategoryType.EXPENSE;
        Category c = new Category(name, type);

        assertThrows(IllegalArgumentException.class,
                () -> c.setName(null));

        assertThrows(IllegalArgumentException.class,
                () -> c.setName(" "));
    }

    @Test
    void setValidType(){
        String name = "TestName";
        CategoryType type = CategoryType.EXPENSE;
        Category c = new Category(name, type);
        c.setType(CategoryType.INCOME);
        assertEquals(CategoryType.INCOME, c.getType());
    }

    @Test
    void setInvalidType(){
        String name = "TestName";
        CategoryType type = CategoryType.EXPENSE;
        Category c = new Category(name, type);
        assertThrows(NullPointerException.class,
                () -> c.setType(null));
    }
}
