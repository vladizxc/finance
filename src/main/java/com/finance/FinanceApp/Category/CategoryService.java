package com.finance.FinanceApp.Category;

import com.finance.FinanceApp.Transaction.TransactionRepository;
import org.springframework.stereotype.Service;

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
}
