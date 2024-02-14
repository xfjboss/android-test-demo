package com.example.virtuesaccumulator.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class VAHammer extends FrameLayout {

    private float lastX, lastY;
    public ImageView imageView;
    public int imageResId;

    public VAHammer(Context context) {
        super(context);
        init();
    }

    public VAHammer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VAHammer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        imageView = new ImageView(getContext());
        imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageResource(imageResId);
        addView(imageView);
    }

    public void setImage(int resId) {
        this.imageResId = resId;
        imageView.setImageResource(imageResId);
    }

    public void setLastPosition(float x, float y) {
        lastX = x;
        lastY = y;
    }
    
    public float getLastX() {
        return this.lastX;
    }
    
    public float getLastY() {
        return this.lastY;
    }
}