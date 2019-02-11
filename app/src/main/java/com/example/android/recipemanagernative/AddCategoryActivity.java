package com.example.android.recipemanagernative;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.recipemanagernative.CategoryRecyclerView.Category;
import com.example.android.recipemanagernative.CategoryRecyclerView.CategoryAdapter;
import com.example.android.recipemanagernative.Database.RecipeManagerDbHelper;

public class AddCategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_confirm_category:
                EditText simpleEditText = (EditText) findViewById(R.id.edit_category_name);
                String strValue = simpleEditText.getText().toString();

                if(InputCheck.getInstance().categoryNameNotNull(strValue)){
                    Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
                    return true;
                }
        }
        Toast.makeText(this, "Not Added", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }
}
