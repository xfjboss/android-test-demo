package com.example.virtuesaccumulator.model;

import android.os.Build;
import android.os.Parcel;

import androidx.annotation.NonNull;

public class VAStickerItem extends VAItem{
    public int resId2;
    public static final String TABLE_NAME = "va_items_sticker";
    public static final String COLUMN_ID = "va_items_id_pri";
    public static final String COLUMN_NAME = "va_items_name";
    public static final String COLUMN_RES_ID_1 = "va_items_res1";
    public static final String COLUMN_RES_ID_2 = "va_items_res2";
    public static final String COLUMN_COST = "va_items_cost";
    public static final String COLUMN_ACQUIRED = "va_items_acquired";//1 true , 0 false
    public static final String COLUMN_EQUIPPED = "va_items_equipped";//1 true , 0 false
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME + " CHAR," +
                    COLUMN_RES_ID_1 + " INTEGER, " +
                    COLUMN_RES_ID_2 + " INTEGER, " +
                    COLUMN_COST + " INTEGER, " +
                    COLUMN_ACQUIRED + " INTEGER, " +
                    COLUMN_EQUIPPED + " INTEGER);";
    public VAStickerItem(Parcel in) {
        super(in);
        resId2 = in.readInt();
    }

    public VAStickerItem(int id, int resId, int resId2, int cost, String name, boolean isAcquired, boolean isEquipped) {
        super(id, resId, cost, name, isAcquired, isEquipped);
        this.resId2 = resId2;
    }

    public VAStickerItem() {
        super();
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(resId2);
    }
}
