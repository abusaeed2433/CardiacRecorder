package com.example.cardiacrecorder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardiacrecorder.R;
import com.example.cardiacrecorder.classes.EachData;

public class RvAdapter extends ListAdapter<EachData, RvAdapter.ViewHolder> {

    private Context mContext;
    private EachData curItem;

    public RvAdapter(Context mContext) {
        super(diffCallback);
        this.mContext = mContext;
    }

    private final static DiffUtil.ItemCallback<EachData> diffCallback = new DiffUtil.ItemCallback<EachData>() {
        @Override
        public boolean areItemsTheSame(@NonNull EachData oldItem, @NonNull EachData newItem) {
            return oldItem.isIdSame(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull EachData oldItem, @NonNull EachData newItem) {
            return oldItem.isFullySame(newItem);
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_data_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        curItem = getItem(position);

        holder.tvDate.setText(curItem.getDate());
        holder.tvSysPressure.setText(curItem.getFormattedSysPressure());
        holder.tvDysPressure.setText(curItem.getFormattedDysPressure());
        holder.tvHeartRate.setText(curItem.getFormattedHeartRate());

        if(curItem.isSysUnusual()){
            holder.ivSysUnusual.setVisibility(View.VISIBLE);
        }
        else{
            holder.ivSysUnusual.setVisibility(View.GONE);
        }

        if(curItem.isDysUnusual()){
            holder.ivDysUnusual.setVisibility(View.VISIBLE);
        }
        else{
            holder.ivDysUnusual.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(view -> {
            //todo handle click here
        });

    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView tvDate, tvSysPressure, tvDysPressure, tvHeartRate;
        private final ImageView ivSysUnusual, ivDysUnusual;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvSysPressure = itemView.findViewById(R.id.tvSysPressure);
            tvDysPressure = itemView.findViewById(R.id.tvDysPressure);
            tvHeartRate = itemView.findViewById(R.id.tvHeartRate);
            ivSysUnusual = itemView.findViewById(R.id.ivSysUnusual);
            ivDysUnusual = itemView.findViewById(R.id.ivDysUnusual);
        }
    }

}
