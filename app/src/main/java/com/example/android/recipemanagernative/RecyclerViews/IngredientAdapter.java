package com.example.android.recipemanagernative.RecyclerViews;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.recipemanagernative.R;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    // Stores the list of ingredients.
    private ArrayList<String> ingredientList;

    // Provides a reference to the views for each data item.
    public static class IngredientViewHolder extends RecyclerView.ViewHolder {

        private TextView ingredientNameTextView; // Holds the text view for the ingredient name.

        private IngredientViewHolder(View view) {
            super(view);

            // Assigns the text view.
            ingredientNameTextView = view.findViewById(R.id.text_view_ingredient_name);
        }
    }

    // Constructor for the IngredientAdapter class.
    public IngredientAdapter(ArrayList<String> ingredientList) {
        this.ingredientList = ingredientList;
    }

    // Creates new views.
    @Override
    @NonNull
    public IngredientAdapter.IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflates the ingredients row view.
        View ingredientRowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ingredient, parent, false);
        return new IngredientViewHolder(ingredientRowView);
    }

    // Replaces the contents of a view.
    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.ingredientNameTextView.setText(ingredientList.get(position));
    }

    // Returns the size of the dataset.
    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    // Updates the list with new data.
    public void updateList(ArrayList<String> ingredientList){

        // Assigns the new ingredient list if it is not empty.
        if(ingredientList != null) {
            this.ingredientList = ingredientList;
        }

        // Refreshes the recycler view if the list is not empty.
        if(ingredientList != null) {
            notifyDataSetChanged();
        }
    }
}