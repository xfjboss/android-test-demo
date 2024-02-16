package com.example.virtuesaccumulator.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.example.virtuesaccumulator.R;

@SuppressLint("ViewConstructor")
public class VASettingButton extends FrameLayout {

    ImageView settingViewIcon;
    ImageView settingViewExplanation;
    int resIcon, resExplanation;
    SettingButtonCallback callback;
    RelativeLayout inner;

    public VASettingButton(Context context) {
        super(context);
        init();
    }

    public VASettingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VASettingButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint({"InflateParams", "ClickableViewAccessibility"})
    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.va_setting_button, this, true);
        inner = findViewById(R.id.va_button_inner);
        settingViewIcon = findViewById(R.id.va_setting_button_icon1);
        settingViewExplanation = findViewById(R.id.va_setting_button_icon2);
        inner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        inner.setScaleX(0.9f);
                        inner.setScaleY(0.9f);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        inner.setScaleX(1.0f);
                        inner.setScaleY(1.0f);
                        if (callback != null) {
                            callback.onButtonClicked();
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void setRes(int resIcon, int resExplanation) {
        this.resIcon = resIcon;
        this.resExplanation = resExplanation;
        if (settingViewIcon == null) {
            settingViewIcon = findViewById(R.id.va_setting_button_icon1);
        }
        if (settingViewExplanation == null) {
            settingViewExplanation = findViewById(R.id.va_setting_button_icon2);
        }
        settingViewIcon.setImageResource(resIcon);
        settingViewExplanation.setImageResource(resExplanation);
    }

    public void setCallback(SettingButtonCallback callback) {
        this.callback = callback;
    }

    public interface SettingButtonCallback {
        void onButtonClicked();
    }}
