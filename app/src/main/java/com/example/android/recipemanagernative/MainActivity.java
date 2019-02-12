package com.example.android.recipemanagernative;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.recipemanagernative.CategoryRecyclerView.CategoryAdapter;
import com.example.android.recipemanagernative.Database.RecipeManagerContract;
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

        /*
        // Creates a recycler view.
        categoryRecyclerView = (RecyclerView) findViewById(R.id.category_recycler_view);

        // Adds a separator to the recycler view.
        categoryRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // Creates a layout manager.
        layoutManager = new LinearLayoutManager(this);

        updateRecyclerView();

        // Creates a recycler view adapter.
        categoryAdapter = new CategoryAdapter(categoryList);

        // Configures the recycler view.
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryRecyclerView.setLayoutManager(layoutManager);*/

        // Gets the database.
        SQLiteDatabase db = RecipeManagerDbHelper.getInstance(this).getReadableDatabase();

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
    protected void onStart() {
        super.onStart();
    }

    private void updateRecyclerView(){
        categoryList.clear();
        Cursor cursor = RecipeManagerDbHelper.getInstance(this).readCategory();
        int categoryNameColumnIndex = cursor.getColumnIndex(RecipeManagerContract.CategoryEntry.COLUMN_CATEGORY_NAME);

        try{
            while(cursor.moveToNext()){
                String categoryName = cursor.getString(categoryNameColumnIndex);
                categoryList.add(new Category(categoryName));
            }
        }
        catch (Exception e){
            Toast.makeText(this, "Error reading database",Toast.LENGTH_SHORT).show();
        }
        finally {
            cursor.close();
        }
    }
}


