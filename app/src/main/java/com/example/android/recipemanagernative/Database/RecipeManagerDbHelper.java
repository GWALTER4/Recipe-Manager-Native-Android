package com.example.android.recipemanagernative.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecipeManagerDbHelper extends SQLiteOpenHelper {

    // Database version number.
    private static final int DATABASE_VERSION = 1;

    // Name of the database file.
    private static final String DATABASE_NAME = "RecipeManager.db";

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
}
