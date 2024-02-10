package com.example.virtuesaccumulator.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.virtuesaccumulator.R;
import com.example.virtuesaccumulator.constant.VAConstDb;
import com.example.virtuesaccumulator.controller.VAController;
import com.example.virtuesaccumulator.model.VAModel;
import com.example.virtuesaccumulator.model.VAModelEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity implements VAController.VAControllerCallback {

    public static int CLICK_TIME_LIMIT = 10;//10ms for one click
    TextView pointCounter;
    TextView coinCounter;
    Button settingButton;
    View woodenFish;
    long currentPoint = 0L;
    int coin = 0;
    VAController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.virtues_main_page);
        pointCounter = findViewById(R.id.total_point);
        coinCounter = findViewById(R.id.total_coin);
        settingButton = findViewById(R.id.setting_button);
        woodenFish = findViewById(R.id.wooden_fish);
        controller = new VAController(this);// better use eventbus and do this on application init.
        currentPoint = controller.getPointData();
        coin = controller.getCoinData();

        pointCounter.setText(String.valueOf(currentPoint));
        coinCounter.setText(String.valueOf(coin));
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        woodenFish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                woodenFish.setEnabled(false);
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        woodenFish.setEnabled(true);
                    }
                }, CLICK_TIME_LIMIT);
                dealWithCount();
            }
        });
    }

    @Override
    protected void onDestroy() {
        saveData();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return false;
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
}



