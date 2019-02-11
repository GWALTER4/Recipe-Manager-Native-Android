package com.example.android.recipemanagernative.CategoryRecyclerView;

public class Category {

    // Name of the category.
    private String categoryName;

    // Constructor for the Category class.
    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    // Gets the category name.
    public String getCategoryName(){
        return this.categoryName;
    }
}
