package com.example.android.recipemanagernative;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.recipemanagernative.Database.RecipeManagerDbHelper;
import com.example.android.recipemanagernative.RecyclerViews.InstructionAdapter;
import com.example.android.recipemanagernative.RecyclerViews.RecipeAdapter;

import java.util.Date;

public class RecipeActivity extends AppCompatActivity {

    static final int IMAGE_CAPTURE_REQUEST = 1; // Request code for the intent.
    private long recipeID; // ID for the recipe being displayed.

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        // Gets the intent that starts this activity.
        Intent categoryActivityIntent = getIntent();

        // Gets the extras from the starting activity.
        recipeID = categoryActivityIntent.getLongExtra(MainActivity.START_MESSAGE, -1);

        // Sets the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar_recipe);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(findRecipeTitle(recipeID));

        if(getSupportActionBar() != null) {
            // Allows up navigation in the toolbar.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            // Shows the home button.
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Creates the recycler view.
        RecyclerView recipeRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_instruction);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        InstructionAdapter instructionAdapter = new InstructionAdapter(RecipeManagerDbHelper.getInstance(this).findInstructions(recipeID));
        recipeRecyclerView.setAdapter(instructionAdapter);

        // Gets a recipe object with the ingredients list and duration.
        Recipe displayRecipe = findRecipeInfo(recipeID);

        // Sets the ingredients list text view.
        TextView ingredientsListTextView = (TextView) findViewById(R.id.text_view_ingredients_list);
        ingredientsListTextView.setText(formatIngredientsList(displayRecipe.getIngredientsListString()));

        // Sets the duration text view.
        TextView durationTextView = (TextView) findViewById(R.id.text_view_heading_duration);
        durationTextView.append(" " + String.valueOf(displayRecipe.getDuration()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_edit_photo:
                editPhoto();

            case R.id.action_delete_recipe:
                createDeleteDialog().show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Creates a dialog to delete the category
    private AlertDialog createDeleteDialog(){

        // Creates a dialog builder to build the dialog.
        AlertDialog.Builder deleteDialogBuilder = new AlertDialog.Builder(this);
        deleteDialogBuilder.setMessage(R.string.dialog_delete_recipe_message)
                .setTitle(R.string.dialog_delete_recipe_title);

        // Creates an on click listener for the positive button.
        deleteDialogBuilder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RecipeManagerDbHelper.getInstance(getApplicationContext()).deleteRecipe(recipeID);
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

    // Finds the recipe title corresponding to the supplied recipe ID.
    private String findRecipeTitle(long recipeID){

        // Gets the category name from the database.
        return RecipeManagerDbHelper.getInstance(this).findRecipeName(recipeID);
    }

    // Finds the ingredients list corresponding to the supplied recipe ID.
    private Recipe findRecipeInfo(long recipeID){

        // Gets the recipe ingredients from the database.
        return RecipeManagerDbHelper.getInstance(this).findRecipeInfo(recipeID);
    }

    // Formats the ingredients list.
    private String formatIngredientsList(String ingredientsList){

        return ingredientsList.replace(",", "\n");
    }


    private void editPhoto(PhotoManager photoManager){

        if(photoManager.hasCamera()){

            // Creates a new intent to have a camera application capture an image and return it.
            Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            // Checks that the device has an app that can handle the intent.
            if(imageCaptureIntent.resolveActivity(getPackageManager()) != null){
                startActivityForResult(imageCaptureIntent, IMAGE_CAPTURE_REQUEST);
            }
        }
    }
}
