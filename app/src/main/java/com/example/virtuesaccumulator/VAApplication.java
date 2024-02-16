package com.example.virtuesaccumulator;

import android.app.Application;

import com.example.virtuesaccumulator.controller.VAController;
import com.example.virtuesaccumulator.helper.DBHelper;
import com.example.virtuesaccumulator.util.VaUtils;

public class VAApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //init database
        DBHelper.initializeInstance(this);
        prepareInitData();
        VaUtils.setGlobalContext(this);
    }

    public void prepareInitData() {
        VAController controller = VAController.getInstance();
        controller.getVAModelData();
    }
}
