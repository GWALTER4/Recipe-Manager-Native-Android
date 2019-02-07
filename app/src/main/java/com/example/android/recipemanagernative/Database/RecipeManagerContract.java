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

        // Name of the recipe.
        // TYPE: TEXT
        public final static String COLUMN_CATEGORY_NAME = "name";

        // Image URI for the recipe image.
        // TYPE: STRING
        public final static String COLUMN_IMAGE_URI = "image_URI";

        // List of recipe ingredients.
        // TYPE: STRING
        public final static String COLUMN_INGREDIENTS_LIST = "ingredients_list";

        // Number of instructions in the recipe.
        // TYPE: INTEGER
        public final static String COLUMN_INSTRUCTION_COUNT = "instruction_count";

        // Total duration of the recipe.
        // TYPE: INTEGER
        public final static String COLUMN_TOTAL_DURATION = "total_duration";
    }
}
