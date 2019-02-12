package com.example.android.recipemanagernative.CategoryRecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.recipemanagernative.Database.RecipeManagerContract;
import com.example.android.recipemanagernative.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private Cursor cursor;

    // Provides a reference to the views for each data item.
    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        public TextView categoryNameTextView;
        public CategoryViewHolder(View view) {
            super(view);
            categoryNameTextView = (TextView) view.findViewById(R.id.category_name_text_view);
        }
    }

    // Constructor for the CategoryAdapter class.
    public CategoryAdapter(Context context, Cursor cursor){
        this.context = context;
        this.cursor = cursor;
    }

    // Creates new views.
    @Override
    @NonNull
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates the category row view.
        View categoryRowView = LayoutInflater.from(this.context).inflate(R.layout.category_row, parent, false);
        return new CategoryViewHolder(categoryRowView);
    }

    // Replace the contents of a view.
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        // Checks the cursor can move to the position.
        if(!cursor.moveToPosition(position)){
            return;
        }

        // Gets the category name from the row in the cursor.
        String categoryName = cursor.getString(cursor.getColumnIndex(RecipeManagerContract.CategoryEntry.COLUMN_CATEGORY_NAME));
        holder.categoryNameTextView.setText(categoryName);
    }

    // Returns the size of the dataset.
    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    // Updates the cursor with new data.
    public void updateCursor(Cursor newCursor){

        // Closes the cursor if it is not empty.
        if(cursor != null) {
            cursor.close();
        }

        // Assigns the new cursor.
        cursor = newCursor;

        // Refreshes the recycler view if the cursor is not empty.
        if(newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
