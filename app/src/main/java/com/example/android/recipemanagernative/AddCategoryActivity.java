package com.example.android.recipemanagernative;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.recipemanagernative.CategoryRecyclerView.Category;
import com.example.android.recipemanagernative.CategoryRecyclerView.CategoryAdapter;
import com.example.android.recipemanagernative.Database.RecipeManagerDbHelper;

public class AddCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflates the activity layout.
        setContentView(R.layout.activity_add_category);

        // Sets the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar_add_category);
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
        getMenuInflater().inflate(R.menu.menu_add_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_confirm_category:
                int code = confirmCategory();
                if(code == 1){
                    Toast.makeText(this, "Category added",Toast.LENGTH_SHORT).show();
                    finish();
                    return true;
                }
                else if(code == 0){
                    Toast.makeText(this, "Invalid category name",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Database error",Toast.LENGTH_SHORT).show();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    // Adds the category to the database.
    public int confirmCategory(){

        // Finds the EditText view and gets the string from it.
        final EditText categoryNameEditText = (EditText) findViewById(R.id.edit_category_name);
        String categoryName = categoryNameEditText.getText().toString();

        // Checks if the category name is valid.
        if(InputCheck.getInstance().isCategoryNameValid(categoryName.trim())){
            long newRowId = RecipeManagerDbHelper.getInstance(this).insertCategory(categoryName);

            // Checks if the category name was inserted into the database.
            // -1 == error, 1 == inserted.
            if(newRowId != -1){
                return 1;
            } else {
                return -1;
            }
        }
        else{
            return 0;
        }
    }
}
