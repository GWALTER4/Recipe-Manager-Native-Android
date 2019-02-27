package com.example.android.recipemanagernative;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddInstructionActivity extends AppCompatActivity {

    private String instructionText; // Stores the text of the instruction.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflates the activity layout.
        setContentView(R.layout.activity_add_instruction);

        // Sets the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar_add_instruction);
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
                if(isInstructionValid()){
                    // Displays a toast message to the user.
                    Toast.makeText(this, "Instruction added",Toast.LENGTH_SHORT).show();

                    // Creates an intent to send the data back to the add recipe activity.
                    Intent confirmInstructionIntent = new Intent();
                    confirmInstructionIntent.putExtra(AddRecipeActivity.GET_INSTRUCTION_MESSAGE, instructionText);
                    setResult(AddRecipeActivity.RESULT_OK, confirmInstructionIntent);
                    finish();
                    return true;
                }
                else{
                    Toast.makeText(this, "Invalid instruction",Toast.LENGTH_SHORT).show();
                }

        }
        return super.onOptionsItemSelected(item);
    }

    // Checks that the instruction is valid.
    private boolean isInstructionValid(){

        // Finds the EditText view and gets the string from it.
        final EditText instructionEditText = (EditText) findViewById(R.id.edit_instruction);
        instructionText = instructionEditText.getText().toString().trim();

        return InputCheck.getInstance().isInstructionTextValid(instructionText);
    }
}
