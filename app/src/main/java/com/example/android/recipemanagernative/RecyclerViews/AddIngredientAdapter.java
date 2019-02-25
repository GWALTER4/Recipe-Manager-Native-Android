package com.example.android.recipemanagernative.RecyclerViews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.recipemanagernative.R;

import java.util.ArrayList;

public class AddIngredientAdapter extends RecyclerView.Adapter<AddIngredientAdapter.IngredientItemViewHolder> {

    private ArrayList<String> ingredientList; // Stores the list of ingredients.
    private OnIngredientClickListener onIngredientClickListener; // Stores an implementation of the OnIngredientClickListener.

    public interface OnIngredientClickListener{
        void onIngredientClick(int position);
    }

    // Provides a reference to the views for each data item.
    public static class IngredientItemViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        private TextView ingredientNameTextView; // Holds the text view for the ingredient name.
        private OnIngredientClickListener onIngredientClickListener; // Holds the click listener interface.

        private IngredientItemViewHolder(View view, OnIngredientClickListener onIngredientClickListener) {
            super(view);

            // Assigns the text view.
            ingredientNameTextView = view.findViewById(R.id.text_view_ingredient_name);

            // Assigns the interface.
            this.onIngredientClickListener = onIngredientClickListener;

            view.setOnLongClickListener(this);
        }

        // Handles when the view is clicked.
        @Override
        public boolean onLongClick(View view){
            onIngredientClickListener.onIngredientClick((int) itemView.getTag());
            return true;
        }
    }

    // Constructor for the AddIngredientAdapter class.
    public AddIngredientAdapter(ArrayList<String> ingredientList, OnIngredientClickListener onIngredientClickListener) {
        this.ingredientList = ingredientList;
        this.onIngredientClickListener = onIngredientClickListener;
    }

    // Creates new views.
    @Override
    @NonNull
    public IngredientItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflates the ingredients row view.
        View ingredientRowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ingredient_item, parent, false);
        return new IngredientItemViewHolder(ingredientRowView, onIngredientClickListener);
    }

    // Replaces the contents of a view.
    @Override
    public void onBindViewHolder(@NonNull IngredientItemViewHolder holder, int position) {

        // Sets the text view to the name of the ingredient at the current position.
        holder.ingredientNameTextView.setText(ingredientList.get(position));

        // Sets the view holder tag to the position in the list so it can be deleted.
        holder.itemView.setTag(position);
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