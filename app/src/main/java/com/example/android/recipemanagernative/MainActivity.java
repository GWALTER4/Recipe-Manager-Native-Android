package com.example.android.recipemanagernative;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.android.recipemanagernative.CategoryRecyclerView.CategoryAdapter;
import com.example.android.recipemanagernative.Database.RecipeManagerDbHelper;

public class MainActivity extends AppCompatActivity {

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
        categoryAdapter = new CategoryAdapter(this, getAllCategories());
        categoryRecyclerView.setAdapter(categoryAdapter);

        // Adds a separator to the recycler view.
        categoryRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // Creates a touch listener to intercept touch events.
        class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {

            private GestureDetector gestureDetector; // Holds a gesture detector object.

            // Constructor for the recycler view touch listener class.
            public RecyclerViewTouchListener(Context context, final RecyclerView recyclerView){

                // Instantiates a new gesture detector object and supplies an on gesture listener.
                gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){

                    // Listens for a single tap.
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {

                        // Finds the view in the recycler view that the user clicked.
                        View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if(child != null) {
                            //int childPosition = recyclerView.getChildLayoutPosition(child);
                            RecyclerViewTouchListener.this.onClick();
                        }
                        return true;
                    }
                });
            }

            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                // Analyzes the motion event and triggers the appropriate callbacks in the gesture
                // listener supplied.
                gestureDetector.onTouchEvent(motionEvent);
                return true;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                // Does not need to do anything.
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }

            // Handles a user clicking on an item in the recycler view.
            public void onClick(){
                // Starts the category activity.
                Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        }

        categoryRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(this, categoryRecyclerView));
    }

    @Override
    protected void onStart(){
        super.onStart();
        categoryAdapter.updateCursor(getAllCategories());
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

    private Cursor getAllCategories() {
        // Reads all the category rows from the database.
        return RecipeManagerDbHelper.getInstance(this).readCategory();
    }
}


