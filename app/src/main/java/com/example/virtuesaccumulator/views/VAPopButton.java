package com.example.virtuesaccumulator.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class VAPopButton extends VAShakeView implements View.OnClickListener {
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
    public void onClick(View v) {
        super.onClick(v);
        callback.onViewClicked();
    }
}
