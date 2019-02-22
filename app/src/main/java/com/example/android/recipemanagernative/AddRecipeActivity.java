package com.example.android.recipemanagernative;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.recipemanagernative.Database.RecipeManagerDbHelper;
import com.example.android.recipemanagernative.RecyclerViews.IngredientAdapter;
import com.example.android.recipemanagernative.RecyclerViews.InstructionAdapter;

import java.util.ArrayList;

public class AddRecipeActivity extends AppCompatActivity implements IngredientAdapter.OnIngredientClickListener, InstructionAdapter.OnInstructionClickListener {

    public static final String GET_INGREDIENT_MESSAGE = "com.example.android.recipemanagernative.GET_INGREDIENT_MESSAGE"; // Stores a string key for intent extras.
    public static final String GET_INSTRUCTION_MESSAGE = "com.example.android.recipemanagernative.GET_INSTRUCTION_MESSAGE"; // Stores a string key for intent extras.
    static final int GET_INGREDIENT_REQUEST = 1; // Request code for intent.
    static final int GET_INSTRUCTION_REQUEST = 2; // Request code for intent.
    private long categoryID; // The category ID of the category the recipe will be added to.
    private IngredientAdapter ingredientAdapter;
    private InstructionAdapter instructionAdapter;
    private ArrayList<String> ingredientList;
    private ArrayList<String> instructionList;
    private int deletePosition;
    private int listToDelete;
    private TextView totalInstructionsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Gets the intent that starts this activity.
        Intent addRecipeActivityIntent = getIntent();

        // Gets the extras from the starting activity.
        categoryID = addRecipeActivityIntent.getLongExtra(CategoryActivity.START_ADD_RECIPE_MESSAGE, -1);

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

        // Initializes the array lists.
        ingredientList = new ArrayList<String>();
        instructionList = new ArrayList<String>();

        // Creates the ingredients recycler view.
        RecyclerView ingredientRecyclerView = (RecyclerView) findViewById(R.id.ingredients_recycler_view);
        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ingredientAdapter = new IngredientAdapter(ingredientList, this);
        ingredientRecyclerView.setAdapter(ingredientAdapter);

        // Creates the instructions recycler view.
        RecyclerView instructionsRecyclerView = (RecyclerView) findViewById(R.id.instructions_recycler_view);
        instructionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        instructionAdapter = new InstructionAdapter(instructionList, this);
        instructionsRecyclerView.setAdapter(instructionAdapter);

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

        // Sets the add instruction button.
        ImageButton addInstructionButton = (ImageButton) findViewById(R.id.button_add_instructions);
        addInstructionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Creates an intent to open the AddInstruction activity.
                Intent addInstructionIntent = new Intent(AddRecipeActivity.this, AddInstructionActivity.class);

                // Starts the new activity for a result.
                startActivityForResult(addInstructionIntent, GET_INSTRUCTION_REQUEST);
            }
        });

        // Sets the total instructions text view.
        totalInstructionsTextView = (TextView) findViewById(R.id.text_view_total_instructions_count);
        totalInstructionsTextView.setText(String.valueOf(instructionList.size()));
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
                int code = confirmRecipe();
                if(code == 1){
                    Toast.makeText(this, "Recipe added",Toast.LENGTH_SHORT).show();
                    finish();
                    return true;
                }
                else if(code == 0){
                    Toast.makeText(this, "Invalid recipe",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Database error",Toast.LENGTH_SHORT).show();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    // Creates a dialog to delete a list item.
    private AlertDialog createDeleteDialog(){

        // Creates a dialog builder to build the dialog.
        AlertDialog.Builder deleteDialogBuilder = new AlertDialog.Builder(this);
        deleteDialogBuilder.setMessage(R.string.dialog_delete_list_item_message)
                .setTitle(R.string.dialog_delete_list_item);

        // Creates an on click listener for the positive button.
        deleteDialogBuilder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(listToDelete == 1){
                    ingredientList.remove(deletePosition);
                    updateIngredientList(ingredientList);
                }
                else{
                    instructionList.remove(deletePosition);
                    updateInstructionList(instructionList);
                    totalInstructionsTextView.setText(String.valueOf(instructionList.size()));
                }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){

        // Check which request is being responded to.
        if(requestCode == GET_INGREDIENT_REQUEST){
            if(resultCode == RESULT_OK){
                String ingredientName = intent.getStringExtra(GET_INGREDIENT_MESSAGE);
                ingredientList.add("• " + ingredientName);
                updateIngredientList(ingredientList);
            }
        }
        else if(requestCode == GET_INSTRUCTION_REQUEST){
            if(resultCode == RESULT_OK){
                String instructionText = intent.getStringExtra(GET_INSTRUCTION_MESSAGE);
                instructionList.add(instructionText);
                updateInstructionList(instructionList);
                totalInstructionsTextView.setText(String.valueOf(instructionList.size()));
            }
        }
    }

    // Updates the ingredient list in the ingredient adapter.
    private void updateIngredientList(ArrayList<String> ingredientList){
        ingredientAdapter.updateList(ingredientList);
    }

    // Updates the instruction list in the instruction adapter.
    private void updateInstructionList(ArrayList<String> instructionList){
        instructionAdapter.updateList(instructionList);
    }

    // Overrides the implementation in the IngredientAdapter.OnIngredientClickListener interface.
    @Override
    public void onIngredientClick(int position){
        deletePosition = position;
        listToDelete = 1;
        createDeleteDialog().show();
    }

    // Overrides the implementation in the InstructionAdapter.OnInstructionClickListener interface.
    @Override
    public void onInstructionClick(int position){
        deletePosition = position;
        listToDelete = 2;
        createDeleteDialog().show();
    }

    private int confirmRecipe(){
        // Finds the EditText views and gets the strings from them.
        final EditText recipeNameEditText = (EditText) findViewById(R.id.edit_recipe_name);
        String recipeName = recipeNameEditText.getText().toString().trim();

        // Finds the EditText views and gets the strings from them.
        final EditText durationEditText = (EditText) findViewById(R.id.edit_duration);
        String duration = durationEditText.getText().toString();

        // Checks if the category name is valid.
        if(InputCheck.getInstance().isRecipeValid(recipeName,ingredientList.size(), instructionList.size(), duration)){
            Recipe recipe = new Recipe(categoryID, recipeName, ingredientList, instructionList, Integer.valueOf(duration));
            long newRowId = RecipeManagerDbHelper.getInstance(this).insertRecipe(categoryID, recipe);

            // Checks if the recipe was inserted into the database.
            // -1 == error, 1 == inserted.
            if(newRowId != -1){
                return 1; // Recipe added.
            } else {
                return -1; // Database error.
            }
        }
        else{
            return 0; // Invalid recipe.
        }
    }

}
