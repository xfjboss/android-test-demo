package com.example.virtuesaccumulator.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.virtuesaccumulator.R;
import com.example.virtuesaccumulator.activity.adapter.StorePageAdapter;
import com.example.virtuesaccumulator.controller.VAController;
import com.example.virtuesaccumulator.model.VABackgroundItem;
import com.example.virtuesaccumulator.model.VAItem;
import com.example.virtuesaccumulator.model.VAItemEvent;
import com.example.virtuesaccumulator.model.VAModelEvent;
import com.example.virtuesaccumulator.model.VAStickerItem;
import com.example.virtuesaccumulator.model.VAWoodenFishItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class VAStoreActivity extends AppCompatActivity {

    private VAController controller;
    private ViewPager2 viewPager;
    private TextView totalCoinView;
    private ImageView backButton;
    private StorePageAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.virtues_store_page);
        viewPager = findViewById(R.id.store_viewPager);
        controller = VAController.getInstance();
        controller.getItemsYouHave();
        totalCoinView = findViewById(R.id.total_coin);
        totalCoinView.setText(String.valueOf(controller.getCoinData()));
        backButton = findViewById(R.id.store_back);

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onItemLoaded(VAItemEvent event) {
        if (event != null && event.dataList != null && !event.dataList.isEmpty()) {
            ArrayList<VAWoodenFishItem> woodenFishItems = new ArrayList<>();
            ArrayList<VAStickerItem> stickerItems = new ArrayList<>();
            ArrayList<VABackgroundItem> backgroundItems = new ArrayList<>();
            for (VAItem item : event.dataList) {
                if (item instanceof VAWoodenFishItem) {
                    woodenFishItems.add((VAWoodenFishItem) item);
                } else if (item instanceof VAStickerItem) {
                    stickerItems.add((VAStickerItem) item);
                } else if (item instanceof VABackgroundItem) {
                    backgroundItems.add((VABackgroundItem) item);
                }
            }
            pagerAdapter = new StorePageAdapter(getSupportFragmentManager(), getLifecycle(), 3);
            viewPager.setAdapter(pagerAdapter);
            pagerAdapter.setWoodenFishItemsList(woodenFishItems);
            pagerAdapter.setStickerItemsList(stickerItems);
            pagerAdapter.setBackgroundItemsList(backgroundItems);
            pagerAdapter.notifyDataSetChanged();
        }
    }
}
