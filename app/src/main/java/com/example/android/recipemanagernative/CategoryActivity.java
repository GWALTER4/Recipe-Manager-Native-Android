package com.example.android.recipemanagernative;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.recipemanagernative.Database.RecipeManagerContract;
import com.example.android.recipemanagernative.Database.RecipeManagerDbHelper;
import com.example.android.recipemanagernative.RecyclerViews.RecipeAdapter;

public class CategoryActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeClickListener {

    public static final String START_RECIPE_MESSAGE = "com.example.android.recipemanagernative.START_RECIPE_MESSAGE"; // Stores a string key for intent extras.
    public static final String START_ADD_RECIPE_MESSAGE = "com.example.android.recipemanagernative.START_ADD_RECIPE_MESSAGE"; // Stores a string key for intent extras.
    private long categoryID; // ID for the category being displayed.
    private RecipeAdapter recipeAdapter; // Stores an adapter for the recycler view.

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Gets the intent that starts this activity.
        Intent categoryActivityIntent = getIntent();

        // Gets the extras from the starting activity.
        categoryID = categoryActivityIntent.getLongExtra(MainActivity.START_MESSAGE, -1);

        // Sets the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar_category);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(findCategoryTitle(categoryID));

        if(getSupportActionBar() != null) {
            // Allows up navigation in the toolbar.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            // Shows the home button.
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Creates the recycler view.
        RecyclerView recipeRecyclerView = (RecyclerView) findViewById(R.id.recipe_recycler_view);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeAdapter = new RecipeAdapter(this, RecipeManagerDbHelper.getInstance(this).findRecipes(categoryID), this);
        recipeRecyclerView.setAdapter(recipeAdapter);

        // Adds a separator to the recycler view.
        recipeRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    @Override
    protected void onStart(){
        super.onStart();
        recipeAdapter.updateCursor(RecipeManagerDbHelper.getInstance(this).findRecipes(categoryID));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_settings:
                // Shows the delete category dialog.
                createDeleteDialog().show();
                return true;

            case R.id.action_add_recipe:
                // Creates an intent to open the AddRecipe activity.
                Intent addRecipeIntent = new Intent(CategoryActivity.this, AddRecipeActivity.class);
                addRecipeIntent.putExtra(START_ADD_RECIPE_MESSAGE, categoryID);
                startActivity(addRecipeIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Creates a dialog to delete the category
    private AlertDialog createDeleteDialog(){

        // Creates a dialog builder to build the dialog.
        AlertDialog.Builder deleteDialogBuilder = new AlertDialog.Builder(this);
        deleteDialogBuilder.setMessage(R.string.dialog_delete_category_message)
                .setTitle(R.string.dialog_delete_category_title);

        // Creates an on click listener for the positive button.
        deleteDialogBuilder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RecipeManagerDbHelper.getInstance(getApplicationContext()).deleteCategory(categoryID);
                finish();
            }
        });

        // Creates an on click listener for the negative button.
        deleteDialogBuilder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Does nothing.
            }
        });

        // Creates and returns the dialog
        return deleteDialogBuilder.create();
    }

    @Override
    // Called when the user chooses to navigate up.
    public boolean onSupportNavigateUp() {
        // Called when the user presses the back key and finishes the activity.
        onBackPressed();
        return true;
    }

    // Finds the category title corresponding to the supplied category ID.
    private String findCategoryTitle(long categoryID){

        // Gets the category name from the database.
        return RecipeManagerDbHelper.getInstance(this).findCategoryName(categoryID);

    }

    @Override
    // Overrides the implementation in the RecipeAdapter.OnRecipeClickListener interface.
    public void onRecipeClick(long recipeID) {

        // Starts the Recipe activity.
        Intent recipeActivityIntent = new Intent(this, RecipeActivity.class);
        recipeActivityIntent.putExtra(START_RECIPE_MESSAGE, recipeID);
        startActivity(recipeActivityIntent);
    }
}
