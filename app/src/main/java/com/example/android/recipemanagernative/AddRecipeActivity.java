package com.example.android.recipemanagernative;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class AddRecipeActivity extends AppCompatActivity {

    public static final String GET_INGREDIENT_MESSAGE = "com.example.android.recipemanagernative.GET_INGREDIENT_MESSAGE"; // Stores a string key for intent extras.
    static final int GET_INGREDIENT_REQUEST = 1; // Request code for intent.
    private TextView ingredientsList; // Text view for the ingredients list.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflates the activity layout.
        setContentView(R.layout.activity_add_recipe);

        // Sets the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar_add_recipe);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            // Allows up navigation in the toolbar.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            // Shows the home button.
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Sets the add ingredient button.
        ImageButton addIngredientButton = (ImageButton) findViewById(R.id.button_add_ingredient);
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Creates an intent to open the AddIngredient activity.
                Intent addIngredientIntent = new Intent(AddRecipeActivity.this, AddIngredientActivity.class);

                // Starts the new activity for a result.
                startActivityForResult(addIngredientIntent, GET_INGREDIENT_REQUEST);
            }
        });

    }

    @Override
    // Called when the user chooses to navigate up.
    public boolean onSupportNavigateUp() {
        // Called when the user presses the back key and finishes the activity.
        onBackPressed();
        return true;
    }

    @Override
    // Creates the options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflates a menu layout.
        getMenuInflater().inflate(R.menu.menu_add_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_confirm_recipe:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){

        // Check which request is being responded to.
        if(requestCode == GET_INGREDIENT_REQUEST){
            if(resultCode == RESULT_OK){

            }
        }
    }

}
