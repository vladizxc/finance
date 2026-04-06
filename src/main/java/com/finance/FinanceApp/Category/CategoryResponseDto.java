package com.finance.FinanceApp.Category;

public class CategoryResponseDto {
    private Long id;
    private String name;
    private CategoryType categoryType;

    public CategoryResponseDto(Long id, String name, CategoryType type){
        if(id == null || id < 0)
            throw new IllegalArgumentException("id cannot be null or negative");
        if(name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be null or blank");
        if (type == null)
            throw new IllegalArgumentException("Type cannot be null");
        this.id = id;
        this.name = name;
        this.categoryType = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if(id == null || id < 0)
            throw new IllegalArgumentException("id cannot be null or negative");
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be null or blank");
        this.name = name;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        if (categoryType == null)
            throw new IllegalArgumentException("Type cannot be null");
        this.categoryType = categoryType;
    }
}
