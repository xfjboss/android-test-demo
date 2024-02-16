package com.example.virtuesaccumulator.activity.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virtuesaccumulator.R;

public class StoreRVViewHolder extends RecyclerView.ViewHolder {

    public ImageView picView;
    public TextView nameView;
    public TextView priceView;
    public RelativeLayout buttonView;
    public StoreRVViewHolder(@NonNull View itemView) {
        super(itemView);
        picView = itemView.findViewById(R.id.va_store_item_pic);
        nameView = itemView.findViewById(R.id.va_store_item_name);
        priceView = itemView.findViewById(R.id.va_store_price_text);
        buttonView = itemView.findViewById(R.id.va_store_purchase_button);
    }
}
