package com.finance.FinanceApp.Category;

import com.finance.FinanceApp.Transaction.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    public CategoryService(CategoryRepository categoryRepository, TransactionRepository transactionRepository){
        if(categoryRepository == null)
            throw new IllegalArgumentException("CategoryRepository cannot be null");
        if(transactionRepository == null){
            throw new IllegalArgumentException("TransactionRepository cannot be null");
        }
        this.categoryRepository = categoryRepository;
        this.transactionRepository = transactionRepository;
    }

    public void createCategory(String name, CategoryType type){
        if(name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be null or empty");
        if(type == null)
            throw new IllegalArgumentException("Type cannot be null");
        if(categoryRepository.existsByName(name))
            throw new IllegalArgumentException("Category with this name already exists");

        Category category = new Category(name, type);
        categoryRepository.save(category);
    }

    public Category getCategoryById(Long id){
        if(id == null || id < 0)
            throw new IllegalArgumentException("Id cannot be null or negative");

        return categoryRepository.findById(id).
                orElseThrow(()-> new RuntimeException("Category not found"));
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Category updateCategory(
            Long id,
            String name,
            CategoryType type
    ){
        if (id == null || id < 0)
            throw new IllegalArgumentException("Invalid id");

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if(name != null && !name.isBlank()){
            if(categoryRepository.existsByName(name)){
                throw new IllegalArgumentException("Category already exists");
            }
            category.setName(name);
        }

        if(type != null){
            category.setType(type);
        }
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id){
        if(id == null || id < 0){
            throw new IllegalArgumentException("Id cannot be null or negative");
        }
        Category category = categoryRepository.findById(id).
                orElseThrow(()-> new NoSuchElementException("Category not found"));
        if(transactionRepository.existsByCategory_Id(id)){
            throw new IllegalArgumentException("Cannot delete because there are Transactions");
        }
        categoryRepository.deleteById(id);
    }
}
