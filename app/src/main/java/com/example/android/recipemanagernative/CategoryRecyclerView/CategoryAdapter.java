package com.example.android.recipemanagernative.CategoryRecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.recipemanagernative.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categoryList;

    // Provides a reference to the views for each data item.
    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        public TextView categoryNameTextView;
        public CategoryViewHolder(View view) {
            super(view);
            categoryNameTextView = (TextView) view.findViewById(R.id.category_name_text_view);
        }
    }

    // Constructor for the CategoryAdapter class.
    public CategoryAdapter(List<Category> categoryList){
        this.categoryList = categoryList;
    }

    // Creates new views.
    @Override
    @NonNull
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflates the view for the list row.
        View categoryRowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_row, parent, false);

        return new CategoryViewHolder(categoryRowView);
    }

    // Replace the contents of a view.
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.categoryNameTextView.setText(category.getCategoryName());
    }

    // Returns the size of the dataset.
    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
