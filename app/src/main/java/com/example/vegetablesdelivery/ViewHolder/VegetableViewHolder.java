package com.example.vegetablesdelivery.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vegetablesdelivery.Interface.ItemClickListener;
import com.example.vegetablesdelivery.R;

public class VegetableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView veg_name;
    public ImageView veg_image;
    private ItemClickListener itemClickListener;

    public VegetableViewHolder(@NonNull View itemView) {
        super(itemView);

        veg_name = itemView.findViewById(R.id.veg_name);
        veg_image = itemView.findViewById(R.id.veg_image);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
