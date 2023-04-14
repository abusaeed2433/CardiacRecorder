package com.example.cardiacrecorder.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardiacrecorder.DetailsPage;
import com.example.cardiacrecorder.R;
import com.example.cardiacrecorder.classes.EachData;
import com.example.cardiacrecorder.others.PopUpListener;

public class RvAdapter extends ListAdapter<EachData, RvAdapter.ViewHolder> {

    private final Context mContext;
    private EachData curItem;
    private PopUpListener popUpListener = null;

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
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_data_layout,parent,false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_data_layout_new,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        curItem = getItem(position);

        holder.tvDateTime.setText(curItem.getSpannableDateTime());
        holder.tvSysPressure.setText(curItem.getSpannableSys());
        holder.tvDysPressure.setText(curItem.getSpannableDys());
        holder.tvHeartRate.setText(curItem.getSpannableHeart());

        holder.tvSysPressure.setBackgroundResource(curItem.getSysBackground());
        holder.tvDysPressure.setBackgroundResource(curItem.getDysBackground());
        holder.tvHeartRate.setBackgroundResource(curItem.getHeartBackground());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext,DetailsPage.class);
            intent.putExtra("data",getItem(holder.getAdapterPosition()));
            mContext.startActivity(intent);
        });

        holder.ivMore.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(mContext,view);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                if(item.getItemId() == R.id.popUpEdit){
                    if(popUpListener!=null) {
                        popUpListener.onEditRequest(getItem(holder.getAdapterPosition()));
                    }
                    return true;
                }
                else if(item.getItemId() == R.id.popUpDelete){
                    if(popUpListener!=null) {
                        popUpListener.onDeleteRequest(getItem(holder.getAdapterPosition()));
                    }
                    return true;
                }
                else {
                    return false;
                }
            });
            popupMenu.show();
        });

    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView tvDateTime, tvSysPressure, tvDysPressure, tvHeartRate;
        private final ImageView ivMore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvSysPressure = itemView.findViewById(R.id.tvSysPressure);
            tvDysPressure = itemView.findViewById(R.id.tvDysPressure);
            tvHeartRate = itemView.findViewById(R.id.tvHeartRate);
            ivMore = itemView.findViewById(R.id.ivMore);
        }
    }

    public void setPopUpListener(PopUpListener popUpListener) {
        this.popUpListener = popUpListener;
    }

}
