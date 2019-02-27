package com.example.android.recipemanagernative.Database;

import android.provider.BaseColumns;

// Specifies the layout of the database schema.
public final class RecipeManagerContract {

    // Prevents the class from accidentally being instantiated.
    private RecipeManagerContract(){}

    // Inner class that defines the Category table contents.
    public static final class CategoryEntry implements BaseColumns{

        // Name of the database table.
        public final static String TABLE_NAME = "category";

        // Unique ID number for the category.
        // TYPE: INTEGER
        public final static String ID = BaseColumns._ID;

        // Name of the category.
        // TYPE: TEXT
        public final static String COLUMN_CATEGORY_NAME = "name";
    }

    // Inner class that defines the Recipe table contents.
    public static final class RecipeEntry implements BaseColumns{

        // Name of the database table.
        public final static String TABLE_NAME = "recipe";

        // Unique ID number for the recipe.
        // TYPE: INTEGER
        public final static String ID = BaseColumns._ID;

        // Unique ID number for the category.
        // TYPE: INTEGER
        public final static String CATEGORY_ID = "category_id";

        // Name of the recipe.
        // TYPE: TEXT
        public final static String COLUMN_RECIPE_NAME = "name";

        // Image URI for the recipe image.
        // TYPE: STRING
        public final static String COLUMN_IMAGE_PATH = "image_path";

        // List of recipe ingredients.
        // TYPE: STRING
        public final static String COLUMN_INGREDIENTS_LIST = "ingredients_list";

        // Number of instructions in the recipe.
        // TYPE: INTEGER
        public final static String COLUMN_INSTRUCTION_COUNT = "instruction_count";

        // Total duration of the recipe.
        // TYPE: INTEGER
        public final static String COLUMN_TOTAL_DURATION = "total_duration";

        // Foreign key for a category.
        // TYPE: INTEGER
        public final static String FK_CATEGORY_ID = "fk_category_id";
    }

    // Inner class that defines the Instruction table contents.
    public static final class InstructionEntry implements BaseColumns{

        // Name of the database table.
        public final static String TABLE_NAME = "instruction";

        // Unique ID number for the instruction.
        // TYPE: INTEGER
        public final static String ID = BaseColumns._ID;

        // Unique ID number for the recipe.
        // TYPE: RECIPE
        public final static String RECIPE_ID = "recipe_id";

        // Sequence number for the instruction.
        // TYPE: INTEGER
        public final static String COLUMN_SEQUENCE_NUMBER = "sequence_number";

        // Description for the instruction.
        // TYPE: STRING
        public final static String COLUMN_DESCRIPTION = "description";

        // Foreign key for a recipe.
        // TYPE: INTEGER
        public final static String FK_RECIPE_ID = "fk_recipe_id";
    }

    // Inner class the defines the table create statements.
    public static final class SQLCreateStatements{

        // Category table create statement.
        public static final String SQL_CREATE_CATEGORY =
                "CREATE TABLE " + CategoryEntry.TABLE_NAME + " (" +
                CategoryEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CategoryEntry.COLUMN_CATEGORY_NAME + " TEXT NOT NULL);";

        // Recipe table create statement.
        public static final String SQL_CREATE_RECIPE =
                "CREATE TABLE " + RecipeEntry.TABLE_NAME + " (" +
                RecipeEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RecipeEntry.CATEGORY_ID + " INTEGER NOT NULL," +
                RecipeEntry.COLUMN_RECIPE_NAME + " TEXT NOT NULL," +
                RecipeEntry.COLUMN_IMAGE_PATH + " TEXT," +
                RecipeEntry.COLUMN_INGREDIENTS_LIST + " TEXT NOT NULL," +
                RecipeEntry.COLUMN_INSTRUCTION_COUNT + " INTEGER NOT NULL," +
                RecipeEntry.COLUMN_TOTAL_DURATION + " INTEGER NOT NULL," +
                "CONSTRAINT " + RecipeEntry.FK_CATEGORY_ID +
                " FOREIGN KEY (" + RecipeEntry.CATEGORY_ID + ") " +
                "REFERENCES " + CategoryEntry.TABLE_NAME + "(" + CategoryEntry.ID + "));";

        // Instruction table create statement.
        public static final String SQL_CREATE_INSTRUCTION =
                "CREATE TABLE " + InstructionEntry.TABLE_NAME + " (" +
                InstructionEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                InstructionEntry.RECIPE_ID + " INTEGER NOT NULL," +
                InstructionEntry.COLUMN_SEQUENCE_NUMBER + " INTEGER NOT NULL," +
                InstructionEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL," +
                "CONSTRAINT " + InstructionEntry.FK_RECIPE_ID +
                " FOREIGN KEY (" + InstructionEntry.RECIPE_ID + ") " +
                "REFERENCES " + RecipeEntry.TABLE_NAME + "(" + RecipeEntry.ID + "));";
    }
}
