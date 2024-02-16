package com.example.virtuesaccumulator.util;

import android.content.Context;

public class VaUtils {

    static Context globalContext;

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static void setGlobalContext(Context context) {
        globalContext = context.getApplicationContext();
    }

    public static Context getGlobalContext() {
        return globalContext;
    }

}
