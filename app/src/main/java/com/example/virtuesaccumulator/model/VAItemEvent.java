package com.example.virtuesaccumulator.model;

import java.util.ArrayList;

public class VAItemEvent {
    public static int RESPONSE_CODE_SUCCESS = 0;
    public static int RESPONSE_CODE_FAIL = 0;
    public ArrayList<? extends VAItem> dataList;
    public int resCode;

    public VAItemEvent(int code, ArrayList<? extends VAItem> dataList) {
        this.dataList = dataList;
        this.resCode = code;
    }
}
