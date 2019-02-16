package com.example.android.recipemanagernative;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.recipemanagernative.CategoryRecyclerView.CategoryAdapter;
import com.example.android.recipemanagernative.Database.RecipeManagerContract;
import com.example.android.recipemanagernative.Database.RecipeManagerDbHelper;

public class CategoryActivity extends AppCompatActivity {

    private long categoryID; // ID for the category being displayed.

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Gets the intent that starts this activity.
        Intent categoryActivityIntent = getIntent();

        // Gets the extras from the starting activity.
        categoryID = categoryActivityIntent.getLongExtra(MainActivity.START_MESSAGE, -1);

        // Sets the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(findCategoryTitle(categoryID));
    }

    // Finds the category title corresponding to the supplied category ID.
    private String findCategoryTitle(long categoryID){

        // Gets a cursor from the database.
        Cursor cursor = RecipeManagerDbHelper.getInstance(this).findCategoryName(categoryID);

        if(cursor != null && cursor.moveToFirst()) {
            String categoryName = cursor.getString(cursor.getColumnIndex(RecipeManagerContract.CategoryEntry.COLUMN_CATEGORY_NAME));
            cursor.close();
            return categoryName;
        }
        return "Error";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_settings:
                // Shows the delete category dialog.
                createDeleteDialog().show();
                return true;

            case R.id.action_add_recipe:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Creates a dialog to delete the category
    private AlertDialog createDeleteDialog(){

        // Creates a dialog builder to build the dialog.
        AlertDialog.Builder deleteDialogBuilder = new AlertDialog.Builder(this);
        deleteDialogBuilder.setMessage(R.string.dialog_delete_category_message)
                .setTitle(R.string.dialog_delete_category_title);

        // Creates an on click listener for the positive button.
        deleteDialogBuilder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RecipeManagerDbHelper.getInstance(getApplicationContext()).deleteCategory(categoryID);
                finish();
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
}
