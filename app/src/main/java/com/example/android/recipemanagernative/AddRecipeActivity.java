package com.example.android.recipemanagernative;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.recipemanagernative.Database.RecipeManagerDbHelper;
import com.example.android.recipemanagernative.RecyclerViews.CategoryAdapter;
import com.example.android.recipemanagernative.RecyclerViews.IngredientAdapter;
import com.example.android.recipemanagernative.RecyclerViews.InstructionAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class AddRecipeActivity extends AppCompatActivity implements IngredientAdapter.OnIngredientClickListener, InstructionAdapter.OnInstructionClickListener {

    public static final String GET_INGREDIENT_MESSAGE = "com.example.android.recipemanagernative.GET_INGREDIENT_MESSAGE"; // Stores a string key for intent extras.
    static final int GET_INGREDIENT_REQUEST = 1; // Request code for intent.
    static final int GET_INSTRUCTION_REQUEST = 2; // Request code for intent.
    private IngredientAdapter ingredientAdapter;
    private InstructionAdapter instructionAdapter;
    private ArrayList<String> ingredientList;
    private ArrayList<String> instructionList;
    private int deletePosition;

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

        // Initializes the array list.
        ingredientList = new ArrayList<String>();

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
                ingredientList.remove(deletePosition);
                updateList(ingredientList);
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
                updateList(ingredientList);
            }
        }
        else if(requestCode == GET_INSTRUCTION_REQUEST){
            if(resultCode == RESULT_OK){

            }
        }
    }

    // Updates the ingredient list in the ingredient adapter.
    private void updateList(ArrayList<String> ingredientList){
        ingredientAdapter.updateList(ingredientList);
    }

    // Overrides the implementation in the IngredientAdapter.OnIngredientClickListener interface.
    @Override
    public void onIngredientClick(int position){
        deletePosition = position;
        createDeleteDialog().show();
    }

    // Overrides the implementation in the InstructionAdapter.OnInstructionClickListener interface.
    @Override
    public void onInstructionClick(int position){

    }


}
