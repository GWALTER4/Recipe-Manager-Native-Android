package com.example.android.recipemanagernative;

import com.example.android.recipemanagernative.Database.RecipeManagerDbHelper;

import java.util.regex.Pattern;

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

    // Checks if a category name contains valid characters.
    public boolean isCategoryNameValid(String categoryName){

        // Uses a regex that only allows alphabetical characters and spaces to
        // validate the user's inputted category name.
        if(Pattern.matches("[a-zA-Z\\s]+",categoryName)){
            return true;
        }
        else{
            return false;
        }
    }


    public boolean isIngredientNameValid(String ingredientName){

        if(Pattern.matches("[a-zA-Z\\s\\d]+", ingredientName)){
            return true;
        }
        else{
            return false;
        }
    }
}
