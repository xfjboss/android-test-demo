package com.example.virtuesaccumulator.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;

public class VAPopButton extends VAShakeView {
    public VAPopButton(Context context) {
        super(context);
    }

    public VAPopButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VAPopButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (callback != null) {
                    callback.adjustAccumulatorPosition(x, y);
                }
                break;

            case MotionEvent.ACTION_MOVE:

                break;

            case MotionEvent.ACTION_UP:
                if (callback != null) {
                    callback.adjustAccumulatorStatus(true, x, y);
                    callback.onViewClicked();
                }
                break;
        }
        return super.onTouch(v, event);
    }
}
