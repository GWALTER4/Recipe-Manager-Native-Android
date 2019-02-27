package com.example.android.recipemanagernative;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddIngredientActivity extends AppCompatActivity {

    private String ingredientName; // Name of the ingredient.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflates the activity layout.
        setContentView(R.layout.activity_add_ingredient);

        // Sets the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar_add_ingredient);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            // Allows up navigation in the toolbar.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            // Shows the home button.
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
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
        getMenuInflater().inflate(R.menu.menu_add_ingredient, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_confirm_ingredient:
                if(confirmIngredient()){
                    Toast.makeText(this, "Ingredient added",Toast.LENGTH_SHORT).show();
                    Intent confirmIngredientIntent = new Intent();
                    confirmIngredientIntent.putExtra(AddRecipeActivity.GET_INGREDIENT_MESSAGE, ingredientName);
                    setResult(AddRecipeActivity.RESULT_OK, confirmIngredientIntent);
                    finish();
                    return true;
                }
                else{
                    Toast.makeText(this, "Invalid ingredient name",Toast.LENGTH_SHORT).show();
                }

        }
        return super.onOptionsItemSelected(item);
    }

    // Checks that the ingredient is valid.
    public boolean confirmIngredient(){

        // Finds the EditText view and gets the string from it.
        final EditText ingredientEditText = (EditText) findViewById(R.id.edit_ingredient);
        ingredientName = ingredientEditText.getText().toString().trim();

        if(InputCheck.getInstance().isIngredientNameValid(ingredientName)){
            return true;
        }
        else
        {
            return false;
        }
    }
}
