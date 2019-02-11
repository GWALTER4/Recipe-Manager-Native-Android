package com.example.android.recipemanagernative.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecipeManagerDbHelper extends SQLiteOpenHelper {

    // Stores an instance of the RecipeManagerDbHelper class.
    private static RecipeManagerDbHelper instance;

    // Database version number.
    private static final int DATABASE_VERSION = 1;

    // Name of the database file.
    private static final String DATABASE_NAME = "RecipeManager.db";

    // Singleton accessor for the RecipeManagerDbHelper class.
    public static synchronized RecipeManagerDbHelper getInstance(Context context){
        if(instance == null) {
            instance = new RecipeManagerDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    // Constructs a new instance of the database.
    public RecipeManagerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
    public void insertCategory(String categoryName) {

        // Gets the database in write mode.
        SQLiteDatabase db = super.getWritableDatabase();

        // Creates a new map of values.
        ContentValues values = new ContentValues();
        values.put(RecipeManagerContract.CategoryEntry.COLUMN_CATEGORY_NAME, categoryName);

        // Inserts a new row into the database.
        db.insert(RecipeManagerContract.CategoryEntry.TABLE_NAME, null, values);
    }
}
