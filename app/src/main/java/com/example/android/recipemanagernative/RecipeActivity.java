package com.example.android.recipemanagernative;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.recipemanagernative.Database.RecipeManagerDbHelper;
import com.example.android.recipemanagernative.RecyclerViews.InstructionAdapter;
import com.example.android.recipemanagernative.RecyclerViews.RecipeAdapter;

public class RecipeActivity extends AppCompatActivity {

    private long recipeID; // ID for the recipe being displayed.
    private InstructionAdapter instructionAdapter; // Stores an adapter for the recycler view.

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
        getSupportActionBar().setTitle(String.valueOf(recipeID));

        if(getSupportActionBar() != null) {
            // Allows up navigation in the toolbar.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            // Shows the home button.
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Creates the recycler view.
        RecyclerView recipeRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_instruction);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //instructionAdapter = new InstructionAdapter( DATABASE QUERY);
        //recipeRecyclerView.setAdapter(recipeAdapter);

        // Adds a separator to the recycler view.
        recipeRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

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
}
