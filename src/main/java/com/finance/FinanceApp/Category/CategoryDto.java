package com.finance.FinanceApp.Category;

public class CategoryDto {
    private String name;
    private CategoryType type;

    public CategoryDto(){}

    public CategoryDto(String name, CategoryType type){
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be null or blank");
        if (type == null)
            throw new IllegalArgumentException("Type cannot be null");

        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be null or blank");
        this.name = name;
    }

    public CategoryType getType() {
        return type;
    }

    public void setType(CategoryType type) {
        if (type == null)
            throw new IllegalArgumentException("Type cannot be null");
        this.type = type;
    }
}
