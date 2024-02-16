package com.example.virtuesaccumulator.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.virtuesaccumulator.R;
import com.example.virtuesaccumulator.constant.VAConstDb;
import com.example.virtuesaccumulator.controller.VAController;
import com.example.virtuesaccumulator.model.VAModel;
import com.example.virtuesaccumulator.model.VAModelEvent;
import com.example.virtuesaccumulator.util.VaUtils;
import com.example.virtuesaccumulator.views.VAHammer;
import com.example.virtuesaccumulator.views.VAPopButton;
import com.example.virtuesaccumulator.views.VASettingDialog;
import com.example.virtuesaccumulator.views.VAShakeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity
        implements VAController.VAControllerCallback, VAShakeView.ShakeViewCallback {

    public static int CLICK_TIME_LIMIT = 10;//10ms for one click
    private TextView pointCounter;
    private TextView coinCounter;
    private ImageView settingButton;
    private VAPopButton woodenFish;
    private VAHammer hammer;
    long currentPoint = 0L;
    int coin = 0;
    VAController controller;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.virtues_main_page);
        pointCounter = findViewById(R.id.total_point);
        coinCounter = findViewById(R.id.total_coin);
        settingButton = findViewById(R.id.setting_button);
        woodenFish = findViewById(R.id.wooden_fish);
        hammer = findViewById(R.id.va_hammer);
        controller = VAController.getInstance();// better use eventbus and do this on application init.
        currentPoint = controller.getPointData();
        coin = controller.getCoinData();

        pointCounter.setText(String.valueOf(currentPoint));
        coinCounter.setText(String.valueOf(coin));
        woodenFish.setClickCallback(this);
        woodenFish.setImage(R.drawable.example);
        hammer.setImage(R.drawable.va_hammer);

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VASettingDialog dialog = new VASettingDialog(MainActivity.this);
                Window window = dialog.getWindow();

                if (window != null) {
                    window.setBackgroundDrawableResource(R.drawable.va_shape_dialog);
                }
                dialog.show();
            }
        });
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveData();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY();
        Log.d("xffffffffffff----------ac", event.toString());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                adjustAccumulatorPosition(x, y);
                break;

            case MotionEvent.ACTION_MOVE:
                float deltaX = x - hammer.getLastX();
                float deltaY = y - hammer.getLastY();

                float translationX = hammer.getTranslationX() + deltaX;
                float translationY = hammer.getTranslationY() + deltaY;

                hammer.setTranslationX(translationX);
                hammer.setTranslationY(translationY);

                hammer.setLastPosition(x, y);
                break;

            case MotionEvent.ACTION_UP:
                adjustAccumulatorStatus(false, x, y);
                break;
        }
        return true;
    }

    public void dealWithCount() {
        currentPoint += 1;
        if (currentPoint % 100L == 0L) {
            coin += 1;
            VAModel vm = new VAModel();
            vm.setCoin(coin);
            vm.setId(VAConstDb.SELF_ID);
            vm.setPoint(currentPoint);
            controller.saveData(vm, this);
        }
        pointCounter.setText(String.valueOf(currentPoint));
        coinCounter.setText(String.valueOf(coin));
    }

    public void saveData() {
        VAModel vm = new VAModel();
        vm.setCoin(coin);
        vm.setId(VAConstDb.SELF_ID);
        vm.setPoint(currentPoint);
        controller.saveData(vm, this);
    }

    public void showPlusOneAni(float x, float y) {
        int animationDuration = 700;

        ImageView plusOne = new ImageView(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                VaUtils.dip2px(this, 60), VaUtils.dip2px(this, 80));
        params.leftMargin = (int) x;
        params.topMargin = (int) y;
        plusOne.setVisibility(View.VISIBLE);
        plusOne.setImageResource(R.drawable.va_plus_one);
        addContentView(plusOne, params);

        //animation part
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, -1f);
        translateAnimation.setDuration(animationDuration);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(animationDuration);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setFillAfter(true);

        plusOne.startAnimation(animationSet);
    }

    @Override
    public void onDatabaseOperationComplete(int resType, int resCode) {//seems meaningless
        if (isFinishing()) {return;}
        if (resCode == 0) {
            switch (resType) {
                case VAController.VAControllerCallback.RESULT_TYPE_ALL_SAVE:
                    break;
                case VAController.VAControllerCallback.RESULT_TYPE_COIN_MODIFY:
                    break;
                case VAController.VAControllerCallback.RESULT_TYPE_POINT_MODIFY:
                    break;
                default:
                    break;
            }
        } else {//error toast
            Toast.makeText(this, "Save error!", Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onDataLoaded(VAModelEvent event) {
        if (event != null && event.resCode == VAModelEvent.RESPONSE_CODE_SUCCESS) {
            currentPoint = event.model.getPoint();
            coin = event.model.getCoin();
            pointCounter.setText(String.valueOf(currentPoint));
            coinCounter.setText(String.valueOf(coin));
        }
    }

    @Override
    public void onViewClicked() {
        woodenFish.setEnabled(false);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                woodenFish.setEnabled(true);
            }
        }, CLICK_TIME_LIMIT);
        dealWithCount();
    }

    @Override
    public void adjustAccumulatorPosition(float x, float y) {
        x = x - VaUtils.dip2px(this, 40);
        y = y - VaUtils.dip2px(this, 65);
        hammer.setImage(R.drawable.va_hammer_down);
        if (hammer.getVisibility() == View.GONE) {
            hammer.setVisibility(View.VISIBLE);
        }
        hammer.setTranslationX(x);
        hammer.setTranslationY(y);
        hammer.setLastPosition(x, y);
    }

    @Override
    public void adjustAccumulatorStatus(boolean addOneShow, float x, float y) {
        hammer.setImage(R.drawable.va_hammer);
        if (addOneShow) {
            y = y - VaUtils.dip2px(this, 85);
            showPlusOneAni(x, y);
        }
    }
}



