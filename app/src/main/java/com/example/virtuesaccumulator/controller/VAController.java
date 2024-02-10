package com.example.virtuesaccumulator.controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;

import com.example.virtuesaccumulator.constant.VAConstDb;
import com.example.virtuesaccumulator.helper.DBHelper;
import com.example.virtuesaccumulator.model.VAModel;
import com.example.virtuesaccumulator.model.VAModelEvent;

import org.greenrobot.eventbus.EventBus;

public class VAController {

    public static VAModel vaModel;
    private final Handler uiHandler;
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private final Context context;

    public VAController(Context context) {
        vaModel = new VAModel();
        this.context = context;
        uiHandler = new Handler(Looper.getMainLooper());
    }
    public void getAllData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                openDatabase();
                Cursor cursor = getData();
                int resCode = VAModelEvent.RESPONSE_CODE_SUCCESS;
                if (cursor.moveToFirst()) {
                    do {
                        @SuppressLint("Range") int coin = cursor.getInt(cursor.getColumnIndex(VAModel.COLUMN_COIN));
                        @SuppressLint("Range") long point = cursor.getLong(cursor.getColumnIndex(VAModel.COLUMN_POINT));
                        vaModel.setCoin(coin);
                        vaModel.setPoint(point);
                    } while (cursor.moveToNext());
                } else {//no data
                    insertData(0, 0L, VAConstDb.SELF_ID);
                    resCode = VAModelEvent.RESPONSE_CODE_FAIL;
                }
                close();
                //send notify
                VAModelEvent event = new VAModelEvent(resCode, vaModel);
                EventBus.getDefault().postSticky(event);
            }
        }).start();
    }

    public long getPointData() {
        return vaModel.getPoint();
    }

    public void savePointNum(long num) {

    }

    public int getCoinData() {
        return vaModel.getCoin();
    }

//    public void saveCoin(int coin, int id, final VAControllerCallback callback) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                openDatabase();
//                int resCode = updateData(coin, null, id);
//                uiHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        callback.onDatabaseOperationComplete(VAControllerCallback.RESULT_TYPE_COIN_MODIFY, resCode);
//                    }
//                });
//                close();
//            }
//        }).start();
//    }

    public void saveData(final VAModel vm, final VAControllerCallback callback) {
        vaModel.setPoint(vm.getPoint());
        vaModel.setCoin(vm.getCoin());
        new Thread(new Runnable() {
            @Override
            public void run() {
                openDatabase();
                int resCode = updateData(vm.getCoin(), vm.getPoint(), vm.getId());
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onDatabaseOperationComplete(VAControllerCallback.RESULT_TYPE_ALL_SAVE, resCode);
                    }
                });
                close();
            }
        }).start();
    }

    public void openDatabase() throws SQLException {
        dbHelper = DBHelper.getInstance(context);
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertData(int coin, long point, int id) {
        ContentValues values = new ContentValues();
        values.put(VAModel.COLUMN_COIN, coin);
        values.put(VAModel.COLUMN_POINT, point);
        values.put(VAModel.COLUMN_ID, id);
        database.insert(VAModel.TABLE_NAME, null, values);
    }

    public int updateData(Integer coin, Long point, int id) {
        ContentValues values = new ContentValues();
        if (coin != null) {
            values.put(VAModel.COLUMN_COIN, coin);
        }
        if (point != null) {
            values.put(VAModel.COLUMN_POINT, point);
        }
        values.put(VAModel.COLUMN_ID, id);
        try {
            database.update(VAModel.TABLE_NAME, values, "id=?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            return 1;
        }
        return 0;
    }

    public Cursor getData() {
        return database.query(VAModel.TABLE_NAME, null, null, null, null, null, null);
    }

    public interface VAControllerCallback {
        int RESULT_TYPE_ALL_SAVE = 0;
        int RESULT_TYPE_COIN_MODIFY = 1;
        int RESULT_TYPE_POINT_MODIFY = 2;
        int RESULT_TYPE_ALL_GET = 3;
        void onDatabaseOperationComplete(int resType, int resCode);
    }
}
