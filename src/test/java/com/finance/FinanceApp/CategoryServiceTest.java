package com.finance.FinanceApp;

import com.finance.FinanceApp.Category.Category;
import com.finance.FinanceApp.Category.CategoryRepository;
import com.finance.FinanceApp.Category.CategoryService;
import com.finance.FinanceApp.Category.CategoryType;
import com.finance.FinanceApp.Transaction.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    CategoryRepository categoryRepository;

    @Mock
    TransactionRepository transactionRepository;

    @Test
    void CategoryServiceConstructor(){
        CategoryService service = new CategoryService(categoryRepository, transactionRepository);
        assertNotNull(service);
    }

    @Test
    void CategoryServiceInvalidConstructor(){
        assertThrows(IllegalArgumentException.class,
                () -> new CategoryService(null, transactionRepository));
        assertThrows(IllegalArgumentException.class,
                () -> new CategoryService(categoryRepository, null));
    }

    @Test
    void createCategoryValidData(){
        CategoryType type = CategoryType.INCOME;
        CategoryService service = new CategoryService(categoryRepository, transactionRepository);

        service.createCategory("Test", type);
        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);

        verify(categoryRepository).save(captor.capture());

        Category saved = captor.getValue();

        assertEquals("Test", saved.getName());
        assertEquals(type, saved.getType());
    }

    @Test
    void createCategoryInvalidData(){
        CategoryType type = CategoryType.INCOME;
        CategoryService service = new CategoryService(categoryRepository, transactionRepository);

        assertThrows(IllegalArgumentException.class,
                ()-> service.createCategory(null, type));

        assertThrows(IllegalArgumentException.class,
                ()-> service.createCategory(" ", type));

        assertThrows(IllegalArgumentException.class,
                ()-> service.createCategory("Test", null));

        verify(categoryRepository, never()).save(any());
    }

    @Test
    void createCategoryDuplicateName(){
        CategoryType type = CategoryType.INCOME;
        CategoryService service = new CategoryService(categoryRepository, transactionRepository);

        when(categoryRepository.existsByName("Test")).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> service.createCategory("Test", type));

        verify(categoryRepository, never()).save(any());
    }
}
