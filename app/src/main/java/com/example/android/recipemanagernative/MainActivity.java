package com.example.android.recipemanagernative;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.recipemanagernative.CategoryRecyclerView.CategoryAdapter;
import com.example.android.recipemanagernative.Database.RecipeManagerDbHelper;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.OnCategoryClickListener {

    // Stores a string key for intent extras.
    public static final String START_MESSAGE = "com.example.android.recipemanagernative.START_MESSAGE";

    // Stores an adapter for the recycler view.
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sets the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        // Creates the recycler view.
        RecyclerView categoryRecyclerView = (RecyclerView) findViewById(R.id.category_recycler_view);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryAdapter = new CategoryAdapter(this, RecipeManagerDbHelper.getInstance(this).findCategories(), this);
        categoryRecyclerView.setAdapter(categoryAdapter);

        // Adds a separator to the recycler view.
        categoryRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    @Override
    protected void onStart(){
        super.onStart();
        categoryAdapter.updateCursor(RecipeManagerDbHelper.getInstance(this).findCategories());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_add_category:
                // Creates an intent to open the AddCategory activity.
                Intent addCategoryIntent = new Intent(MainActivity.this, AddCategoryActivity.class);

                // Starts the new activity.
                startActivity(addCategoryIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    // Overrides the implementation in the CategoryAdapter.OnCategoryClickListener interface.
    public void onCategoryClick(long categoryID) {

        // Starts the Category activity.
        Intent categoryActivityIntent = new Intent(this, CategoryActivity.class);
        categoryActivityIntent.putExtra(START_MESSAGE, categoryID);
        startActivity(categoryActivityIntent);
    }
}


