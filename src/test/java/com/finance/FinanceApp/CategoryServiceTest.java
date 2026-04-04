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

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    @Test
    void getCategoryByIdInvalidId(){
        CategoryService service = new CategoryService(categoryRepository, transactionRepository);

        assertThrows(IllegalArgumentException.class,
                () -> service.getCategoryById(null));

        assertThrows(IllegalArgumentException.class,
                () -> service.getCategoryById(-1L));
    }

    @Test
    void getCategoryByIdShouldThrowWhenNotFound(){
        Long id = 1L;

        when(categoryRepository.findById(id))
                .thenReturn(Optional.empty());

        CategoryService service = new CategoryService(categoryRepository, transactionRepository);

        assertThrows(RuntimeException.class,
                ()-> service.getCategoryById(id));
    }

    @Test
    void getCategoryByIdValid(){
        Long id = 1L;
        CategoryType type = CategoryType.INCOME;
        Category category = new Category("Test", type);
        CategoryService service = new CategoryService(categoryRepository, transactionRepository);
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        Category result = service.getCategoryById(id);
        verify(categoryRepository).findById(id);
        assertEquals(category, result);
    }

    @Test
    void getAllCategories(){
        CategoryService service = new CategoryService(categoryRepository, transactionRepository);
        List<Category> allCategories = service.getAllCategories();
        verify(categoryRepository).findAll();
    }

    @Test
    void updateCategoryShouldUpdateOnlyName(){
        CategoryType type = CategoryType.INCOME;
        Long id = 1L;
        Category category = new Category("Test", type);
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        CategoryService service = new CategoryService(categoryRepository, transactionRepository);
        service.updateCategory(id,"New", null);

        assertEquals("New", category.getName());
        assertEquals(type, category.getType());
        verify(categoryRepository).save(category);
    }

    @Test
    void updateCategoryShouldUpdateOnlyType(){
        CategoryType type = CategoryType.INCOME;
        CategoryType newType = CategoryType.EXPENSE;
        Long id = 1L;
        Category category = new Category("Test", type);
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        CategoryService service = new CategoryService(categoryRepository, transactionRepository);
        service.updateCategory(id,null, newType);

        assertEquals("Test", category.getName());
        assertEquals(newType, category.getType());
        verify(categoryRepository).save(category);
    }

    @Test
    void updateCategoryNotUpdateOnAllNull(){
        CategoryType type = CategoryType.INCOME;
        Long id = 1L;
        Category category = new Category("Test", type);
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        CategoryService service = new CategoryService(categoryRepository, transactionRepository);
        service.updateCategory(id,null, null);

        assertEquals("Test", category.getName());
        assertEquals(type, category.getType());
        verify(categoryRepository).save(category);
    }

    @Test
    void updateCategoryNotUpdateOnBlankString(){
        CategoryType type = CategoryType.INCOME;
        Long id = 1L;
        Category category = new Category("Test", type);
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        CategoryService service = new CategoryService(categoryRepository, transactionRepository);
        service.updateCategory(id," ", null);

        assertEquals("Test", category.getName());
        assertEquals(type, category.getType());
        verify(categoryRepository).save(category);
    }

    @Test
    void updateCategoryShouldThrowOnInvalidId(){
        CategoryService service = new CategoryService(categoryRepository, transactionRepository);

        assertThrows(IllegalArgumentException.class,
                ()-> service.updateCategory(-1L, null, null));

        assertThrows(IllegalArgumentException.class,
                ()-> service.updateCategory(null, null, null));
    }

    @Test
    void updateCategoryShouldThrowWhenNotFound(){
        Long id = 1L;

        when(categoryRepository.findById(id)).thenReturn(Optional.empty());
        CategoryService service = new CategoryService(categoryRepository, transactionRepository);

        assertThrows(RuntimeException.class,
                ()-> service.updateCategory(id, null, null));

        verify(categoryRepository, never()).save(any());
    }

    @Test
    void updateCategoryDuplicateName(){
        CategoryType type = CategoryType.INCOME;
        Long id = 1L;
        Category category = new Category("Test", type);
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(categoryRepository.existsByName("Test")).thenReturn(true);
        CategoryService service = new CategoryService(categoryRepository, transactionRepository);

        assertThrows(IllegalArgumentException.class,
                ()-> service.updateCategory(1L,"Test", null));

        verify(categoryRepository, never()).save(any());
    }

    @Test
    void deleteCategoryInvalidId(){
        CategoryService service = new CategoryService(categoryRepository, transactionRepository);

        assertThrows(IllegalArgumentException.class,
                () -> service.deleteCategory(null));

        assertThrows(IllegalArgumentException.class,
                () -> service.deleteCategory(-1L));
    }

    @Test
    void deleteCategoryShouldThrowWhenNotFound(){
        CategoryService service = new CategoryService(categoryRepository, transactionRepository);
        Long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,
                ()-> service.deleteCategory(id));
        verify(categoryRepository, never()).deleteById(id);
    }

    @Test
    void deleteCategoryShouldThrowWhenTransactions(){
        CategoryService service = new CategoryService(categoryRepository, transactionRepository);
        CategoryType type = CategoryType.INCOME;
        Long id = 1L;
        Category category = new Category("Test", type);
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(transactionRepository.existsByCategory_Id(id)).thenReturn(true);
        assertThrows(IllegalArgumentException.class,
                () -> service.deleteCategory(id));
        verify(categoryRepository, never()).deleteById(id);
    }

    @Test
    void deleteCategoryValid(){
        CategoryService service = new CategoryService(categoryRepository, transactionRepository);
        CategoryType type = CategoryType.INCOME;
        Long id = 1L;
        Category category = new Category("Test", type);
        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(transactionRepository.existsByCategory_Id(id)).thenReturn(false);
        service.deleteCategory(id);
        verify(categoryRepository).deleteById(id);
    }
}
