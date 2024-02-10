package com.example.virtuesaccumulator.model;

public class VAModelEvent {

    public static int RESPONSE_CODE_SUCCESS = 0;
    public static int RESPONSE_CODE_FAIL = 0;
    public VAModel model;
    public int resCode;

    public VAModelEvent(int code, VAModel model) {
        this.model = model;
        this.resCode = code;
    }
}
