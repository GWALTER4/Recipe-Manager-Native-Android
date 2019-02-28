package com.example.android.recipemanagernative.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.example.android.recipemanagernative.Recipe;

import java.io.File;
import java.util.List;

public class RecipeManagerDbHelper extends SQLiteOpenHelper {

    // Stores an instance of the RecipeManagerDbHelper class.
    private static RecipeManagerDbHelper instance;

    // Database version number.
    private static final int DATABASE_VERSION = 1;

    // Name of the database file.
    private static final String DATABASE_NAME = "RecipeManager.db";

    // Stores a connection to the database in write mode.
    private SQLiteDatabase writeDB;

    // Stores a connection to the database in read mode.
    private SQLiteDatabase readDB;

    // Singleton accessor for the RecipeManagerDbHelper class.
    public static synchronized RecipeManagerDbHelper getInstance(Context context){
        if(instance == null) {
            instance = new RecipeManagerDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    // Constructs a new instance of the database.
    private RecipeManagerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        // Gets the database in write mode.
        writeDB = super.getWritableDatabase();

        // Gets the database in read mode.
        readDB = super.getReadableDatabase();
    }

    // Called when the database is created for the first time.
    public void onCreate(SQLiteDatabase db) {

        // Executes the create table statements.
        db.execSQL(RecipeManagerContract.SQLCreateStatements.SQL_CREATE_CATEGORY);
        db.execSQL(RecipeManagerContract.SQLCreateStatements.SQL_CREATE_RECIPE);
        db.execSQL(RecipeManagerContract.SQLCreateStatements.SQL_CREATE_INSTRUCTION);
    }

    // Called when the database needs to be upgraded.
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Inserts a category into the database.
    public long insertCategory(String categoryName) {

        // Creates a new map of values.
        ContentValues values = new ContentValues();
        values.put(RecipeManagerContract.CategoryEntry.COLUMN_CATEGORY_NAME, categoryName);

        // Inserts a new row into the database.
        return writeDB.insert(RecipeManagerContract.CategoryEntry.TABLE_NAME, null, values);
    }

    // Reads the categories from the database.
    public Cursor findCategories(){

        // Defines a projection that specifies which columns from the database the query will use.
        String[] projection = {
                RecipeManagerContract.CategoryEntry.ID,
                RecipeManagerContract.CategoryEntry.COLUMN_CATEGORY_NAME
        };

        // Executes the query.
        return readDB.query(
                RecipeManagerContract.CategoryEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
    }

    // Finds the name of a category corresponding to the supplied category ID.
    public String findCategoryName(Long categoryID){

        // Defines a projection.
        String[] projection = {
                RecipeManagerContract.CategoryEntry.COLUMN_CATEGORY_NAME
        };

        // Filters the query results.
        String selection = RecipeManagerContract.CategoryEntry.ID + " = ?";
        String[] selectionArgs = {categoryID.toString()};

        // Gets a cursor from the database.
        Cursor cursor =  readDB.query(
                RecipeManagerContract.CategoryEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if(cursor != null && cursor.moveToFirst()) {
            String categoryName = cursor.getString(cursor.getColumnIndex(RecipeManagerContract.CategoryEntry.COLUMN_CATEGORY_NAME));
            cursor.close();
            return categoryName;
        }
        else{
            cursor.close();
        }

        return "Error";
    }

    // Finds the name of a recipe corresponding to the supplied recipe ID.
    public String findRecipeName(Long recipeID){

        // Defines a projection.
        String[] projection = {
                RecipeManagerContract.RecipeEntry.COLUMN_RECIPE_NAME
        };

        // Filters the query results.
        String selection = RecipeManagerContract.RecipeEntry.ID + " = ?";
        String[] selectionArgs = {recipeID.toString()};

        // Gets a cursor from the database.
        Cursor cursor =  readDB.query(
                RecipeManagerContract.RecipeEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if(cursor != null && cursor.moveToFirst()) {
            String categoryName = cursor.getString(cursor.getColumnIndex(RecipeManagerContract.RecipeEntry.COLUMN_RECIPE_NAME));
            cursor.close();
            return categoryName;
        }
        else{
            cursor.close();
        }

        return "Error";
    }

    // Deletes a category.
    public void deleteCategory(Long categoryID) {

        // Deletes all recipes in the category.
        deleteAllRecipes(categoryID);

        String selection = RecipeManagerContract.CategoryEntry.ID + " LIKE ?";
        String[] selectionArgs = {categoryID.toString()};
        writeDB.delete(RecipeManagerContract.CategoryEntry.TABLE_NAME, selection, selectionArgs);
    }

    // Reads the recipes from a particular category from the database.
    public Cursor findRecipes(Long categoryID){

        // Defines a projection that specifies which columns from the database the query will use.
        String[] projection = {
                RecipeManagerContract.RecipeEntry.ID,
                RecipeManagerContract.RecipeEntry.CATEGORY_ID,
                RecipeManagerContract.RecipeEntry.COLUMN_RECIPE_NAME,
                RecipeManagerContract.RecipeEntry.COLUMN_IMAGE_PATH,
                RecipeManagerContract.RecipeEntry.COLUMN_INGREDIENTS_LIST,
                RecipeManagerContract.RecipeEntry.COLUMN_INSTRUCTION_COUNT,
                RecipeManagerContract.RecipeEntry.COLUMN_TOTAL_DURATION
        };

        // Filters the query results.
        String selection = RecipeManagerContract.RecipeEntry.CATEGORY_ID + " = ?";
        String[] selectionArgs = {categoryID.toString()};

        // Executes the query.
        return readDB.query(
                RecipeManagerContract.RecipeEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }

    // Inserts a recipe into the database.
    public long insertRecipe(long categoryID, Recipe recipe){

        long recipeNewRowID; // Stores the ID of a new recipe row.
        long instructionNewRowID; // Stores the ID of a new instruction row.

        // Creates a new map of values.
        ContentValues values = new ContentValues();
        values.put(RecipeManagerContract.RecipeEntry.CATEGORY_ID, categoryID);
        values.put(RecipeManagerContract.RecipeEntry.COLUMN_RECIPE_NAME, recipe.getRecipeName());
        values.put(RecipeManagerContract.RecipeEntry.COLUMN_INGREDIENTS_LIST, concatIngredientsList(recipe.getIngredientsList()));
        values.put(RecipeManagerContract.RecipeEntry.COLUMN_INSTRUCTION_COUNT, recipe.getTotalInstructions());
        values.put(RecipeManagerContract.RecipeEntry.COLUMN_TOTAL_DURATION, recipe.getDuration());

        // Inserts a recipe into the database.
        recipeNewRowID = writeDB.insert(RecipeManagerContract.RecipeEntry.TABLE_NAME, null, values);

        // Returns the error code if an error occurred.
        if(recipeNewRowID == -1){
            return recipeNewRowID;
        }

        // Iterates through all the instructions.
        for(int i = 0; i < recipe.getTotalInstructions(); i++){

            // Inserts an instruction into the database.
            instructionNewRowID = insertInstruction(recipeNewRowID, i + 1, recipe.getInstructionList().get(i));

            // Returns the error code if an error occurred.
            if(instructionNewRowID == -1){
                return instructionNewRowID;
            }
        }

        // Returns the ID of the new recipe.
        return recipeNewRowID;
    }

    // Inserts an instruction into the database.
    private long insertInstruction(long recipeNewRowID, int sequenceNumber, String instructionDescription){
        ContentValues values = new ContentValues();
        values.put(RecipeManagerContract.InstructionEntry.RECIPE_ID, recipeNewRowID);
        values.put(RecipeManagerContract.InstructionEntry.COLUMN_SEQUENCE_NUMBER, sequenceNumber);
        values.put(RecipeManagerContract.InstructionEntry.COLUMN_DESCRIPTION, instructionDescription);

        return writeDB.insert(RecipeManagerContract.InstructionEntry.TABLE_NAME, null, values);
    }

    // Concatenates all ingredients list items into one string for easier storage.
    public String concatIngredientsList(List<String> ingredientsList){

        String ingredients = "";

        for(int i = 0; i < ingredientsList.size(); i++){
            ingredients = ingredients + ingredientsList.get(i) + ",";
        }

        return ingredients;
    }

    // Deletes a recipe.
    public void deleteRecipe(Long recipeID) {
        String selection = RecipeManagerContract.RecipeEntry.ID + " LIKE ?";
        String[] selectionArgs = {recipeID.toString()};
        writeDB.delete(RecipeManagerContract.RecipeEntry.TABLE_NAME, selection, selectionArgs);
    }

    // Deletes all recipes belonging to a particular category.
    private void deleteAllRecipes(Long categoryID){
        String selection = RecipeManagerContract.RecipeEntry.CATEGORY_ID + " LIKE ?";
        String[] selectionArgs = {categoryID.toString()};
        writeDB.delete(RecipeManagerContract.RecipeEntry.TABLE_NAME,selection,selectionArgs);
    }

    // Reads the instructions for a particular recipe from the database.
    public Cursor findInstructions(Long recipeID){

        // Defines a projection that specifies which columns from the database the query will use.
        String[] projection = {
                RecipeManagerContract.InstructionEntry.ID,
                RecipeManagerContract.InstructionEntry.COLUMN_SEQUENCE_NUMBER,
                RecipeManagerContract.InstructionEntry.COLUMN_DESCRIPTION
        };

        // Filters the query results.
        String selection = RecipeManagerContract.InstructionEntry.RECIPE_ID + " = ?";
        String[] selectionArgs = {recipeID.toString()};

        // Executes the query.
        return readDB.query(
                RecipeManagerContract.InstructionEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }

    // Reads the ingredients and duration for a particular recipe from the database.
    public Recipe findRecipeInfo(Long recipeID){

        // Defines a projection that specifies which columns from the database the query will use.
        String[] projection = {
                RecipeManagerContract.RecipeEntry.COLUMN_INGREDIENTS_LIST,
                RecipeManagerContract.RecipeEntry.COLUMN_TOTAL_DURATION
        };

        // Filters the query results.
        String selection = RecipeManagerContract.RecipeEntry.ID + " = ?";
        String[] selectionArgs = {recipeID.toString()};

        // Executes the query.
        Cursor cursor =  readDB.query(
                RecipeManagerContract.RecipeEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if(cursor != null && cursor.moveToFirst()) {
            String ingredientsList =  cursor.getString(cursor.getColumnIndex(RecipeManagerContract.RecipeEntry.COLUMN_INGREDIENTS_LIST));
            int duration = cursor.getInt(cursor.getColumnIndex(RecipeManagerContract.RecipeEntry.COLUMN_TOTAL_DURATION));
            cursor.close();

            return new Recipe(ingredientsList, duration);
        }
        else{
            cursor.close();
        }

        return new Recipe("Error", -1);
    }

    // Updates the image file path for a recipe.
    public void updateRecipeImageFilePath(Long recipeID, String newFilePath){

        // Deletes the old recipe image if one exists.
        deleteOldImage(recipeID);

        // Creates a new map of values.
        ContentValues values = new ContentValues();
        values.put(RecipeManagerContract.RecipeEntry.COLUMN_IMAGE_PATH, newFilePath);

        // Filters the query results.
        String selection = RecipeManagerContract.RecipeEntry.ID + " LIKE ?";
        String[] selectionArgs = {recipeID.toString()};

        // Updates the database.
        writeDB.update(
                RecipeManagerContract.RecipeEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public void deleteOldImage(Long recipeID){

        // Defines a projection that specifies which columns from the database the query will use.
        String[] projection = {
                RecipeManagerContract.RecipeEntry.COLUMN_IMAGE_PATH
        };

        // Filters the query results.
        String selection = RecipeManagerContract.RecipeEntry.ID + " = ?";
        String[] selectionArgs = {recipeID.toString()};

        // Executes the query.
        Cursor cursor =  readDB.query(
                RecipeManagerContract.RecipeEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if(cursor != null && cursor.moveToFirst()){

            // Gets the file path from the cursor.
            String filePath = cursor.getString(cursor.getColumnIndex(RecipeManagerContract.RecipeEntry.COLUMN_IMAGE_PATH));
            if(filePath != null){

                // Gets the old image file.
                File oldImageFile = new File(filePath);

                // Deletes the old image file.
                oldImageFile.delete();
            }
        }
    }
}
