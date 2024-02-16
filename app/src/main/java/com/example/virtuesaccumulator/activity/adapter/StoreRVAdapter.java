package com.example.virtuesaccumulator.activity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.virtuesaccumulator.R;
import com.example.virtuesaccumulator.model.VAItem;

import java.util.ArrayList;

public class StoreRVAdapter extends RecyclerView.Adapter<StoreRVViewHolder> {

    public int count;
    private ArrayList<? extends VAItem> dataList;

    @NonNull
    @Override
    public StoreRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.virtues_store_rv_item, parent, false);
        return new StoreRVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreRVViewHolder holder, int position) {
        VAItem item = dataList.get(position);
        holder.picView.setImageResource(item.resId);
        holder.priceView.setText(String.valueOf(item.cost));
        holder.nameView.setText(item.name);
        holder.buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public void setData(ArrayList<? extends VAItem> data) {
        if (data != null) {
            dataList = data;
            count = data.size();
        } else {
            dataList = new ArrayList<>();
            count = 0;
        }
    }
}
