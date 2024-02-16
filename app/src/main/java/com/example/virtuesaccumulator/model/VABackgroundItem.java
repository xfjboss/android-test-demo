package com.example.virtuesaccumulator.model;

import android.os.Parcel;

public class VABackgroundItem extends VAItem{
    public static final String TABLE_NAME = "va_items_background";
    public static final String COLUMN_ID = "va_items_id_pri";
    public static final String COLUMN_NAME = "va_items_name";
    public static final String COLUMN_RES_ID_1 = "va_items_res1";
    public static final String COLUMN_COST = "va_items_cost";
    public static final String COLUMN_ACQUIRED = "va_items_acquired";//1 true , 0 false
    public static final String COLUMN_EQUIPPED = "va_items_equipped";//1 true , 0 false
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_RES_ID_1 + " INTEGER, " +
                    COLUMN_NAME + " CHAR, " +
                    COLUMN_COST + " INTEGER, " +
                    COLUMN_ACQUIRED + " INTEGER, " +
                    COLUMN_EQUIPPED + " INTEGER);";
    protected VABackgroundItem(Parcel in) {
        super(in);
    }

    public VABackgroundItem() {
        super();
    }

    public VABackgroundItem(int id, int resId, int cost, String name, boolean isAcquired, boolean isEquipped) {
        super(id, resId, cost, name, isAcquired, isEquipped);
    }
}
