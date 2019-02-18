package com.example.android.recipemanagernative.RecyclerViews;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.recipemanagernative.Database.RecipeManagerContract;
import com.example.android.recipemanagernative.R;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Context context; // Holds the application context.
    private Cursor cursor; // Holds a cursor object.
    private OnRecipeClickListener onRecipeClickListener; // Stores an implementation of the OnRecipeClickListener.

    // Provides a method to handle when recipes are clicked.
    public interface OnRecipeClickListener{
        void onRecipeClick(long recipeID);
    }

    // Provides a reference to the views for each data item.
    // Provides a reference to the views for each data item.
    public static class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView recipeImageView; // Holds the image view for the recipe list item.
        private TextView recipeNameTextView; // Holds the text view for the recipe name.
        private TextView recipeStepsTextView; // Holds the text view for the recipe steps.
        private TextView recipeDurationTextView; // Holds the text view for the recipe duration.
        private OnRecipeClickListener onRecipeClickListener; // Holds the click listener interface.

        // Constructor for the view holder.
        private RecipeViewHolder(View view, OnRecipeClickListener onRecipeClickListener) {
            super(view);

            // Assigns the image view.
            recipeImageView = (ImageView) view.findViewById(R.id.recipe_list_item_image_view);

            // Assigns the text views.
            recipeNameTextView = (TextView) view.findViewById(R.id.recipe_name_text_view);
            recipeStepsTextView = (TextView) view.findViewById(R.id.recipe_steps_text_view);
            recipeDurationTextView = (TextView) view.findViewById(R.id.recipe_duration_text_view);

            // Assigns the interface.
            this.onRecipeClickListener = onRecipeClickListener;

            view.setOnClickListener(this);
        }

        @Override
        // Handles when the view holder is clicked.
        public void onClick(View v) {
            // Calls the click listener interface.
            onRecipeClickListener.onRecipeClick((Long) itemView.getTag());
        }
    }

    // Constructor for the RecipeAdapter class.
    public RecipeAdapter(Context context, Cursor cursor, OnRecipeClickListener onRecipeClickListener){
        this.context = context;
        this.cursor = cursor;
        this.onRecipeClickListener = onRecipeClickListener;
    }

    // Creates new views.
    @Override
    @NonNull
    public RecipeAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates the recipe row view.
        View recipeRowView = LayoutInflater.from(this.context).inflate(R.layout.recipe_row, parent, false);
        return new RecipeViewHolder(recipeRowView, onRecipeClickListener);
    }

    // Replaces the contents of a view.
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        // Checks the cursor can move to the position.
        if(!cursor.moveToPosition(position)){
            return;
        }

        // Gets the date from the cursor.
        String recipeImageURI = cursor.getString(cursor.getColumnIndex(RecipeManagerContract.RecipeEntry.COLUMN_IMAGE_URI));
        String recipeName = cursor.getString(cursor.getColumnIndex(RecipeManagerContract.RecipeEntry.COLUMN_RECIPE_NAME));
        int recipeSteps = cursor.getInt(cursor.getColumnIndex(RecipeManagerContract.RecipeEntry.COLUMN_INSTRUCTION_COUNT));
        int recipeDuration = cursor.getInt(cursor.getColumnIndex(RecipeManagerContract.RecipeEntry.COLUMN_TOTAL_DURATION));
        long recipeID = cursor.getLong(cursor.getColumnIndex(RecipeManagerContract.RecipeEntry.ID));

        // Sets the views with the data from the cursor.
        holder.recipeImageView.setImageURI(Uri.parse(recipeImageURI));
        holder.recipeNameTextView.setText(recipeName);
        holder.recipeStepsTextView.setText(recipeSteps);
        holder.recipeDurationTextView.setText(recipeDuration);
        holder.itemView.setTag(recipeID);
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

