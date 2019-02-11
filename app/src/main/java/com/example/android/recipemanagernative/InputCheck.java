package com.example.android.recipemanagernative;

import com.example.android.recipemanagernative.Database.RecipeManagerDbHelper;

public class InputCheck {

    // Stores an instance of the InputCheck class.
    private static InputCheck instance;

    // Singleton accessor for InputCheck class.
    public static synchronized InputCheck getInstance(){
        if(instance == null) {
            instance = new InputCheck();
        }
        return instance;
    }

    public boolean categoryNameNotNull(String categoryName){
        if(categoryName.equals(null) || categoryName.equals("")) {
            return false;
        }
            return true;
    }
}
