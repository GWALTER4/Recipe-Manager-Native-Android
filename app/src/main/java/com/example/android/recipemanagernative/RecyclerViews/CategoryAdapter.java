package com.example.android.recipemanagernative.RecyclerViews;

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

    private Context context; // Holds the application context.
    private Cursor cursor; // Holds a cursor object.
    private OnCategoryClickListener onCategoryClickListener; // Stores an implementation of the OnCategoryClickListener

    // Provides a method to handle when categories are clicked.
    public interface OnCategoryClickListener{
        void onCategoryClick(long categoryID);
    }

    // Provides a reference to the views for each data item.
    public static class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView categoryNameTextView; // Holds the text view for the category name.
        private OnCategoryClickListener onCategoryClickListener; // Holds the click listener interface.

        // Constructor for the view holder.
        private CategoryViewHolder(View view, OnCategoryClickListener onCategoryClickListener) {
            super(view);

            // Assigns the text view.
            categoryNameTextView = (TextView) view.findViewById(R.id.category_name_text_view);

            // Assigns the interface.
            this.onCategoryClickListener = onCategoryClickListener;

            view.setOnClickListener(this);
        }

        @Override
        // Handles when the view holder is clicked.
        public void onClick(View v) {
            // Calls the click listener interface.
            onCategoryClickListener.onCategoryClick((Long) itemView.getTag());
        }
    }

    // Constructor for the CategoryAdapter class.
    public CategoryAdapter(Context context, Cursor cursor, OnCategoryClickListener onCategoryClickListener){
        this.context = context;
        this.cursor = cursor;
        this.onCategoryClickListener = onCategoryClickListener;
    }

    // Creates new views.
    @Override
    @NonNull
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates the category row view.
        View categoryRowView = LayoutInflater.from(this.context).inflate(R.layout.row_category, parent, false);
        return new CategoryViewHolder(categoryRowView, onCategoryClickListener);
    }

    // Replaces the contents of a view.
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        // Checks the cursor can move to the position.
        if(!cursor.moveToPosition(position)){
            return;
        }

        // Gets the category name from the row in the cursor.
        String categoryName = cursor.getString(cursor.getColumnIndex(RecipeManagerContract.CategoryEntry.COLUMN_CATEGORY_NAME));
        long categoryID = cursor.getLong(cursor.getColumnIndex(RecipeManagerContract.CategoryEntry.ID));

        // Sets the text of the view to the category name.
        holder.categoryNameTextView.setText(categoryName);
        holder.itemView.setTag(categoryID);
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
