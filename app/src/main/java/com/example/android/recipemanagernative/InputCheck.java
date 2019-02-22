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
        return Pattern.matches("[a-zA-Z\\s]+",categoryName);
    }

    // Checks if an ingredient name contains valid characters.
    public boolean isIngredientNameValid(String ingredientName){
        // Uses a regex that only allows alphabetical characters, spaces, and digits to
        // validate the user's inputted ingredient name.
        return Pattern.matches("[a-zA-Z\\s\\d]+", ingredientName);
    }

    // Checks if an instruction text contains valid characters.
    public boolean isInstructionTextValid(String instructionText){
        // Uses a regex that only allows alphabetical characters, spaces, digits,
        // and common punctuation to validate the user's inputted instruction text.
        return Pattern.matches("[a-zA-Z\\s\\d()\\-.,]+", instructionText);
    }

    // Checks if a recipe contains valid data.
    public boolean isRecipeValid(String recipeName, int ingredientsListSize, int instructionListSize, String duration) {

        if(isRecipeNameValid(recipeName) && areRecipeListsValid(ingredientsListSize, instructionListSize)
            && isDurationValid(duration)){
            return true;
        }
        else{
            return false;
        }
    }

    // Checks if a recipe name contains valid characters.
    private boolean isRecipeNameValid(String recipeName){
        // Uses a regex that only allows alphabetical characters and spaces to
        // validate the user's inputted recipe name.
        return Pattern.matches("[a-zA-Z\\s]+",recipeName);
    }

    // Checks that the ingredients and instruction lists are not empty.
    private boolean areRecipeListsValid(int ingredientsListSize, int instructionListSize){

        if(ingredientsListSize > 0 && instructionListSize > 0) {
            return true;
        }
        else{
            return false;
        }
    }

    // Checks if the duration is not null and the integer version is not equal to 0.
    private boolean isDurationValid(String duration){

        if(duration.equals("") ){
            return false;
        }
        else if(Integer.valueOf(duration) == 0){
            return false;
        }
        else{
            return true;
        }
    }
}
