package com.example.virtuesaccumulator.activity.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.virtuesaccumulator.activity.fragment.StoreFragment;
import com.example.virtuesaccumulator.model.VABackgroundItem;
import com.example.virtuesaccumulator.model.VAStickerItem;
import com.example.virtuesaccumulator.model.VAWoodenFishItem;

import java.util.ArrayList;

public class StorePageAdapter extends FragmentStateAdapter {

    private ArrayList<VAStickerItem> stickerItemsList = new ArrayList<>();
    private ArrayList<VABackgroundItem> backgroundItemsList = new ArrayList<>();
    private ArrayList<VAWoodenFishItem> woodenFishItemsList = new ArrayList<>();
    int counts;

    public StorePageAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int count) {
        super(fragmentManager, lifecycle);
        counts = count;
    }

    public void setStickerItemsList(ArrayList<VAStickerItem> list) {
        this.stickerItemsList = list;
    }

    public void setBackgroundItemsList(ArrayList<VABackgroundItem> list) {
        this.backgroundItemsList = list;
    }

    public void setWoodenFishItemsList(ArrayList<VAWoodenFishItem> list) {
        this.woodenFishItemsList = list;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return StoreFragment.getFragment(stickerItemsList);
            case 2:
                return StoreFragment.getFragment(backgroundItemsList);
            default://0
                return StoreFragment.getFragment(woodenFishItemsList);
        }
    }

    @Override
    public int getItemCount() {
        return counts;
    }
}
