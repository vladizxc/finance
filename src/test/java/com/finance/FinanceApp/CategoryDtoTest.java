package com.finance.FinanceApp;

import com.finance.FinanceApp.Category.Category;
import com.finance.FinanceApp.Category.CategoryDto;
import com.finance.FinanceApp.Category.CategoryType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryDtoTest {
    @Test
    void CategoryDtoEmptyConstructor(){
        CategoryDto dto = new CategoryDto();
        assertNotNull(dto);
    }

    @Test
    void CategoryDtoValidConstructor(){
        CategoryDto dto = new CategoryDto("Test", CategoryType.EXPENSE);
        assertEquals("Test", dto.getName());
        assertEquals(CategoryType.EXPENSE, dto.getType());
    }

    @Test
    void CategoryDtoShouldThrowOnInvalidData(){
        String name = "TestName";
        CategoryType type = CategoryType.EXPENSE;

        assertThrows(IllegalArgumentException.class,
                () -> new CategoryDto(null, type));

        assertThrows(IllegalArgumentException.class,
                () -> new CategoryDto(" ", type));

        assertThrows(IllegalArgumentException.class,
                () -> new CategoryDto(name, null));
    }

    @Test
    void setNameValidTest(){
        String name = "TestName";
        CategoryType type = CategoryType.EXPENSE;
        CategoryDto c = new CategoryDto(name, type);
        c.setName("NewName");
        assertEquals("NewName", c.getName());
    }

    @Test
    void setInvalidName(){
        String name = "TestName";
        CategoryType type = CategoryType.EXPENSE;
        CategoryDto c = new CategoryDto(name, type);

        assertThrows(IllegalArgumentException.class,
                () -> c.setName(null));

        assertThrows(IllegalArgumentException.class,
                () -> c.setName(" "));
    }

    @Test
    void setValidType(){
        String name = "TestName";
        CategoryType type = CategoryType.EXPENSE;
        CategoryDto c = new CategoryDto(name, type);
        c.setType(CategoryType.INCOME);
        assertEquals(CategoryType.INCOME, c.getType());
    }

    @Test
    void setInvalidType(){
        String name = "TestName";
        CategoryType type = CategoryType.EXPENSE;
        CategoryDto c = new CategoryDto(name, type);
        assertThrows(IllegalArgumentException.class,
                () -> c.setType(null));
    }
}
