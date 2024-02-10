package com.example.virtuesaccumulator.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.virtuesaccumulator.constant.VAConstDb;
import com.example.virtuesaccumulator.helper.DBHelper;

public class VAModel {
    public static final String TABLE_NAME = "va_table";
    public static final String COLUMN_COIN = "va_coin";
    public static final String COLUMN_POINT = "va_point";
    public static final String COLUMN_ID = "id";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_COIN + " INTEGER, " +
                    COLUMN_POINT + " INTEGER);";
    public int id;
    public long point;
    public int coin;

    public VAModel() {
        this.id = VAConstDb.SELF_ID;
        this.coin = 0;
        this.point = 0L;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setPoint(long point) {
        this.point = point;
    }

    public long getPoint() {
        return this.point;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getCoin() {
        return this.coin;
    }

}
