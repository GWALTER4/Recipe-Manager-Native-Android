package com.example.android.recipemanagernative;

import java.util.List;

public class Recipe {

    private long categoryID; // Stores the categoryID of the category the recipe belongs to.
    private String recipeName; // Stores the name of the recipe.
    private List<String> ingredientsList; // Stores the list of ingredients.
    private List<String> instructionList; // Stores the list of instructions.
    private int totalInstructions; // Stores the total number of instructions.
    private int duration; // Stores the duration of the recipe.

    // Constructor for the Recipe class.
    public Recipe(long categoryID, String recipeName, List<String> ingredientsList,
                List<String> instructionList, int duration){

        this.categoryID = categoryID;
        this.recipeName = recipeName;
        this.ingredientsList = ingredientsList;
        this.instructionList = instructionList;
        this.totalInstructions = instructionList.size();
        this.duration = duration;
    }

    public long getCategoryID(){
        return categoryID;
    }

    public String getRecipeName(){
        return recipeName;
    }

    public List<String> getIngredientsList(){
        return ingredientsList;
    }

    public List<String> getInstructionList(){
        return instructionList;
    }

    public int getTotalInstructions(){
        return totalInstructions;
    }

    public int getDuration(){
        return duration;
    }

}
