package com.example.android.recipemanagernative.RecyclerViews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.recipemanagernative.R;

import java.util.ArrayList;

public class AddInstructionAdapter extends RecyclerView.Adapter<AddInstructionAdapter.InstructionItemViewHolder> {

    private ArrayList<String> instructionList; // Stores the list of instructions.
    private OnInstructionClickListener onInstructionClickListener; // Stores an implementation of the OnInstructionClickListener.

    public interface OnInstructionClickListener{
        void onInstructionClick(int position);
    }

    // Provides a reference to the views for each data item.
    public static class InstructionItemViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        private TextView instructionNumberTextView; // Holds the text view for the instruction number.
        private TextView instructionDescriptionTextView; // Holds the text view for the instruction description.
        private OnInstructionClickListener onInstructionClickListener; // Holds the click listener interface.

        private InstructionItemViewHolder(View view, OnInstructionClickListener onInstructionClickListener) {
            super(view);

            // Assigns the text views.
            instructionNumberTextView = view.findViewById(R.id.text_view_instruction_number);
            instructionDescriptionTextView = view.findViewById(R.id.text_view_instruction_preview);

            // Assigns the interface.
            this.onInstructionClickListener = onInstructionClickListener;

            view.setOnLongClickListener(this);
        }

        // Handles when the view is clicked.
        @Override
        public boolean onLongClick(View view){
            onInstructionClickListener.onInstructionClick((int) itemView.getTag());
            return true;
        }
    }

    // Constructor for the AddIngredientAdapter class.
    public AddInstructionAdapter(ArrayList<String> instructionList, OnInstructionClickListener onInstructionClickListener) {
        this.instructionList = instructionList;
        this.onInstructionClickListener = onInstructionClickListener;
    }

    // Creates new views.
    @Override
    @NonNull
    public InstructionItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflates the ingredients row view.
        View instructionRowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_instruction_item, parent, false);
        return new InstructionItemViewHolder(instructionRowView, onInstructionClickListener);
    }

    // Replaces the contents of a view.
    @Override
    public void onBindViewHolder(@NonNull InstructionItemViewHolder holder, int position) {

        // Sets the text view to the current position.
        holder.instructionNumberTextView.setText(String.valueOf(position + 1));

        // Sets the text view to the description of the instruction at the current position.
        holder.instructionDescriptionTextView.setText(instructionList.get(position));

        // Sets the view holder tag to the position in the list so it can be deleted.
        holder.itemView.setTag(position);
    }

    // Returns the size of the dataset.
    @Override
    public int getItemCount() {
        return instructionList.size();
    }

    // Updates the list with new data.
    public void updateList(ArrayList<String> instructionList){

        // Assigns the new ingredient list if it is not empty.
        if(instructionList != null) {
            this.instructionList = instructionList;
        }

        // Refreshes the recycler view if the list is not empty.
        if(instructionList != null) {
            notifyDataSetChanged();
        }
    }
}
