package com.finance.FinanceApp;

import com.finance.FinanceApp.Category.CategoryResponseDto;
import com.finance.FinanceApp.Category.CategoryType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CategoryResponseDtoTest {

    @Test
    void ConstructorValidTest(){
        Long id = 1L;
        String name = "Test";
        CategoryType type = CategoryType.INCOME;
        CategoryResponseDto c = new CategoryResponseDto(id, name, type);
        assertEquals(id, c.getId());
        assertEquals(name, c.getName());
        assertEquals(type, c.getCategoryType());
    }

    @Test
    void ConstructorInvalidTest(){
        Long id = 1L;
        String name = "Test";
        CategoryType type = CategoryType.INCOME;

        assertThrows(IllegalArgumentException.class,
                () -> new CategoryResponseDto(null, name, type));

        assertThrows(IllegalArgumentException.class,
                () -> new CategoryResponseDto(-1L, name, type));

        assertThrows(IllegalArgumentException.class,
                () -> new CategoryResponseDto(id, null, type));

        assertThrows(IllegalArgumentException.class,
                () -> new CategoryResponseDto(id, " ", type));

        assertThrows(IllegalArgumentException.class,
                () -> new CategoryResponseDto(id, name, null));
    }

    @Test
    void setNameValid(){
        Long id = 1L;
        String name = "Test";
        CategoryType type = CategoryType.INCOME;
        CategoryResponseDto c = new CategoryResponseDto(id, name, type);
        String newName = "Test2";
        c.setName(newName);
        assertEquals(newName, c.getName());
    }

    @Test
    void setNameInvalid(){
        Long id = 1L;
        String name = "Test";
        CategoryType type = CategoryType.INCOME;
        CategoryResponseDto c = new CategoryResponseDto(id, name, type);

        assertThrows(IllegalArgumentException.class,
                () -> c.setName(" "));

        assertThrows(IllegalArgumentException.class,
                () -> c.setName(null));
    }

    @Test
    void setIdValid(){
        Long id = 1L;
        String name = "Test";
        CategoryType type = CategoryType.INCOME;
        CategoryResponseDto c = new CategoryResponseDto(id, name, type);
        Long newId = 2L;
        c.setId(newId);
        assertEquals(newId, c.getId());
    }

    @Test
    void setIdInvalid(){
        Long id = 1L;
        String name = "Test";
        CategoryType type = CategoryType.INCOME;
        CategoryResponseDto c = new CategoryResponseDto(id, name, type);

        assertThrows(IllegalArgumentException.class,
                () -> c.setId(null));

        assertThrows(IllegalArgumentException.class,
                () -> c.setId(-1L));
    }

    @Test
    void setTypeValid(){
        Long id = 1L;
        String name = "Test";
        CategoryType type = CategoryType.INCOME;
        CategoryResponseDto c = new CategoryResponseDto(id, name, type);
        CategoryType newType = CategoryType.EXPENSE;
        c.setCategoryType(newType);
        assertEquals(newType, c.getCategoryType());
    }

    @Test
    void setTypeInvalid(){
        Long id = 1L;
        String name = "Test";
        CategoryType type = CategoryType.INCOME;
        CategoryResponseDto c = new CategoryResponseDto(id, name, type);

        assertThrows(IllegalArgumentException.class,
                () -> c.setCategoryType(null));
    }
}
