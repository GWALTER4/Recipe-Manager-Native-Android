package com.example.android.recipemanagernative;

import java.util.ArrayList;
import java.util.List;

// Manages the ingredient and instruction lists for the AddRecipeActivty.
public class AddRecipeListsManager {

    private ArrayList<String> ingredientList; // Stores the list of ingredients.
    private ArrayList<String> instructionList; // Stores the list of instructions.

    // Constructor for the AddRecipeListsManager class.
    public AddRecipeListsManager(){
        ingredientList = new ArrayList<String>();
        instructionList = new ArrayList<String>();
    }

    // Gets the ingredient list.
    public ArrayList<String> getIngredientList(){
        return ingredientList;
    }

    // Gets the instruction list.
    public ArrayList<String> getInstructionList(){
        return instructionList;
    }

    // Adds an ingredient.
    public void addIngredient(String ingredient){
        ingredientList.add(ingredient);
    }

    // Adds an instruction.
    public void addInstruction(String instruction){
        instructionList.add(instruction);
    }

    // Removes an ingredient.
    public void removeIngredient(int position){
        ingredientList.remove(position);
    }

    // Removes an instruction.
    public void removeInstruction(int position){
        instructionList.remove(position);
    }
}
