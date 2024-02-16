package com.example.virtuesaccumulator.helper;

import static com.example.virtuesaccumulator.constant.VAConstDb.DATABASE_NAME;
import static com.example.virtuesaccumulator.constant.VAConstDb.DATABASE_VERSION;
import static com.example.virtuesaccumulator.model.VAModel.CREATE_TABLE;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.virtuesaccumulator.model.VABackgroundItem;
import com.example.virtuesaccumulator.model.VAModel;
import com.example.virtuesaccumulator.model.VAStickerItem;
import com.example.virtuesaccumulator.model.VAWoodenFishItem;

public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper instance;

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context.getApplicationContext());
        }
        return instance;
    }

    public static void initializeInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context.getApplicationContext());
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(VAModel.CREATE_TABLE);
        db.execSQL(VAWoodenFishItem.CREATE_TABLE);
        db.execSQL(VAStickerItem.CREATE_TABLE);
        db.execSQL(VABackgroundItem.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // update app update db
        for (int version = oldVersion + 1; version <= newVersion; version++) {
            switch (version) {
                case 2:
                    db.execSQL(VAWoodenFishItem.CREATE_TABLE);
                    db.execSQL(VAStickerItem.CREATE_TABLE);
                    db.execSQL(VABackgroundItem.CREATE_TABLE);
                    break;
                default:
                    break;
            }
        }
    }
}
