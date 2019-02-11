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

import com.example.android.recipemanagernative.CategoryRecyclerView.CategoryAdapter;
import com.example.android.recipemanagernative.Database.RecipeManagerDbHelper;
import com.example.android.recipemanagernative.CategoryRecyclerView.Category;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;
    private RecyclerView.Adapter categoryAdapter;
    private RecyclerView.LayoutManager layoutManager;

    // GUT
    private List<Category> categoryList = new ArrayList<Category>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sets the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        categoryRecyclerView = (RecyclerView) findViewById(R.id.category_recycler_view);
        categoryRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        layoutManager = new LinearLayoutManager(this);
        categoryAdapter = new CategoryAdapter(/* GUT*/categoryList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryRecyclerView.setLayoutManager(layoutManager);

        // Gets the database.
        SQLiteDatabase db = RecipeManagerDbHelper.getInstance(this).getReadableDatabase();

        // GUT
        categoryList.add(new Category("Breakfast"));
        categoryList.add(new Category("Lunch"));
        categoryList.add(new Category("Dinner"));

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
}
