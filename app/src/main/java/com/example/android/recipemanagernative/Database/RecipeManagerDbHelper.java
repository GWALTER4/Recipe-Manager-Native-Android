package com.example.android.recipemanagernative.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

        //return cursor;
    }

    // Finds the name of a category corresponding to the supplied category ID.
    public Cursor findCategoryName(Long categoryID){

        // Defines a projection.
        String[] projection = {
                RecipeManagerContract.CategoryEntry.COLUMN_CATEGORY_NAME
        };

        // Filters the query results.
        String selection = RecipeManagerContract.CategoryEntry.ID + " = ?";
        String[] selectionArgs = {categoryID.toString()};

        return readDB.query(
                RecipeManagerContract.CategoryEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }

    public void deleteCategory(Long categoryID) {
        String selection = RecipeManagerContract.CategoryEntry.ID + " LIKE ?";
        String[] selectionArgs = {categoryID.toString()};
        writeDB.delete(RecipeManagerContract.CategoryEntry.TABLE_NAME, selection, selectionArgs);
    }
}
