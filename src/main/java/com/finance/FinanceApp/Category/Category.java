package com.finance.FinanceApp.Category;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType type;

    public Category(){}

    public Category(String name, CategoryType type){
        if(name== null || name.isBlank()) throw new IllegalArgumentException("Name must have name");
        if(type == null) throw new NullPointerException("Type cannot be null");
        this.name = name;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CategoryType getType() {
        return type;
    }

    public void setName(String name) {
        if(name== null || name.isBlank()) throw new IllegalArgumentException("Name must have name");
        this.name = name;
    }

    public void setType(CategoryType type) {
        if(type == null) throw new NullPointerException("Type cannot be null");
        this.type = type;
    }
}
