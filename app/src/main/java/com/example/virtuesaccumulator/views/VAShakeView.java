package com.example.virtuesaccumulator.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class VAShakeView extends FrameLayout implements View.OnTouchListener{
    public int imageResId;
    public int animationPeriod = 300;
    public ImageView imageView;
    public ShakeViewCallback callback;

    public VAShakeView(Context context) {
        super(context);
        init();
    }

    public VAShakeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VAShakeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        imageView = new ImageView(getContext());
        imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageResource(imageResId);
        addView(imageView);
        setOnTouchListener(this);
    }

    private int getAnimationDuration() {
        return animationPeriod;
    }

    public void setAnimationPeriod(int time) {
        this.animationPeriod = time;
    }

    public void setImage(int resId) {
        this.imageResId = resId;
        imageView.setImageResource(imageResId);
    }

    public void setClickCallback(ShakeViewCallback callback) {
        this.callback = callback;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("xffffffffffff----------pop", event.toString());
        float x = event.getRawX();
        float y = event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                ScaleAnimation bounceAnimation = new ScaleAnimation(1f, 1.2f, 1f, 1.2f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f
                );

                bounceAnimation.setDuration(getAnimationDuration());
                bounceAnimation.setInterpolator(new BounceInterpolator());

                startAnimation(bounceAnimation);
                break;
        }
        return false;
    }

    public interface ShakeViewCallback {
        void onViewClicked();

        void adjustAccumulatorPosition(float x, float y);

        void adjustAccumulatorStatus(boolean addOneShow, float x, float y);
    }
}
