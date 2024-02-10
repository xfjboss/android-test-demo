package com.example.virtuesaccumulator;

import android.app.Application;

import com.example.virtuesaccumulator.controller.VAController;
import com.example.virtuesaccumulator.helper.DBHelper;

public class VAApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //init database
        DBHelper.initializeInstance(this);
        prepareInitData();
    }

    public void prepareInitData() {
        VAController controller = new VAController(this);
        controller.getAllData();
    }
}
