package com.example.cardiacrecorder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardiacrecorder.R;

import java.util.ArrayList;
import java.util.Arrays;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.ViewHolder> {

    private final Context mContext;
    private final ArrayList<String> options;
    private int selectedPosition = -1;

    public OptionAdapter(@NonNull Context mContext, String ...args) {
        this.mContext = mContext;
        this.options = new ArrayList<>();
        this.options.addAll(Arrays.asList(args));
    }

    /**
     * creates ViewHolder
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_option,parent,false));
    }

    /**
     * sets data in layout
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvOptionText.setText(options.get(position));
        if(position == selectedPosition){
            holder.itemView.setBackgroundResource(R.drawable.single_option_checked_background);
            holder.tvOptionText.setCompoundDrawablesWithIntrinsicBounds(
                    ResourcesCompat.getDrawable(mContext.getResources(),R.drawable.baseline_check_24,null),
                     null, null, null);
            holder.tvOptionText.setTextColor(mContext.getResources().getColor(R.color.blue));
        }
        else{
            holder.itemView.setBackgroundResource(R.drawable.single_option_background);
            holder.tvOptionText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            holder.tvOptionText.setTextColor(mContext.getResources().getColor(R.color.text_color));
        }


        holder.itemView.setOnClickListener(view -> {
            int pos = holder.getAdapterPosition();
            if(selectedPosition == pos){
                selectedPosition = -1;
                notifyItemChanged(pos);
            }
            else {
                if(selectedPosition != -1){
                    notifyItemChanged(selectedPosition); // prev item
                }
                selectedPosition = pos;
                notifyItemChanged(selectedPosition);
            }
        });

    }

    /**
     * Item counter
     * @return number of items
     */
    @Override
    public int getItemCount() {
        return options.size();
    }


    /**
     * Resets position
     */
    public void resetFilter(){
        if(selectedPosition != -1){
            int pos = selectedPosition;
            selectedPosition = -1;
            notifyItemChanged(pos);
        }
    }

    /**
     * getter for position
     * @return position
     */
    public int getSelectedPosition(){
        return selectedPosition;
    }

    /**
     * ViewHolder class
     */
    static class ViewHolder extends RecyclerView.ViewHolder{
        final TextView tvOptionText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOptionText = itemView.findViewById(R.id.tvOption);
        }

    }

}
