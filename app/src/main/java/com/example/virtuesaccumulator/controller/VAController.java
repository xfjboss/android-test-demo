package com.example.virtuesaccumulator.controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;

import com.example.virtuesaccumulator.R;
import com.example.virtuesaccumulator.constant.VAConstDb;
import com.example.virtuesaccumulator.helper.DBHelper;
import com.example.virtuesaccumulator.model.VABackgroundItem;
import com.example.virtuesaccumulator.model.VAItem;
import com.example.virtuesaccumulator.model.VAItemEvent;
import com.example.virtuesaccumulator.model.VAModel;
import com.example.virtuesaccumulator.model.VAModelEvent;
import com.example.virtuesaccumulator.model.VAStickerItem;
import com.example.virtuesaccumulator.model.VAWoodenFishItem;
import com.example.virtuesaccumulator.util.VaUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class VAController {

    @SuppressLint("StaticFieldLeak")
    public static VAController instance;
    public VAModel vaModel;
    private Handler uiHandler;
    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public VAController() {}

    public static VAController getInstance() {
        if (instance == null) {
            instance = new VAController();
            instance.vaModel = new VAModel();
            instance.uiHandler = new Handler(Looper.getMainLooper());
        }
        return instance;
    }
    public void getVAModelData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                openDatabase();
                Cursor cursor = getDataStatementVAModel();
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
                //send notify
                VAModelEvent event = new VAModelEvent(resCode, vaModel);
                EventBus.getDefault().postSticky(event);
                close();
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

    public void getItemsYouHave() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                openDatabase();
                int resCode = VAItemEvent.RESPONSE_CODE_SUCCESS;
                ArrayList<VAItem> resList = new ArrayList<>();

                Cursor cursor = getDataStatementVAItemsWooden();
                if (cursor.moveToFirst()) {
                    do {
                        VAWoodenFishItem item = new VAWoodenFishItem();
                        @SuppressLint("Range") String name =
                                cursor.getString(cursor.getColumnIndex(VAWoodenFishItem.COLUMN_NAME));
                        @SuppressLint("Range") int cost =
                                cursor.getInt(cursor.getColumnIndex(VAWoodenFishItem.COLUMN_COST));
                        @SuppressLint("Range") int id =
                                cursor.getInt(cursor.getColumnIndex(VAWoodenFishItem.COLUMN_ID));
                        @SuppressLint("Range") int resId =
                                cursor.getInt(cursor.getColumnIndex(VAWoodenFishItem.COLUMN_RES_ID_1));
                        @SuppressLint("Range") int isAcquired =
                                cursor.getInt(cursor.getColumnIndex(VAWoodenFishItem.COLUMN_ACQUIRED));
                        @SuppressLint("Range") int isEquipped =
                                cursor.getInt(cursor.getColumnIndex(VAWoodenFishItem.COLUMN_EQUIPPED));
                        item.name = name;
                        item.id = id;
                        item.cost = cost;
                        item.resId = resId;
                        item.isAcquired = isAcquired == 1;
                        item.isEquipped = isEquipped == 1;
                        resList.add(item);
                    } while (cursor.moveToNext());
                } else {//no data
                    ArrayList<VAWoodenFishItem> list = new ArrayList<>();
                    list.add(new VAWoodenFishItem(0, R.drawable.example, 100,
                            "xf0", false, false));
                    list.add(new VAWoodenFishItem(1, R.drawable.example, 200,
                            "xf1", false, false));
                    list.add(new VAWoodenFishItem(2, R.drawable.example, 300,
                            "xf2", false, false));
                    list.add(new VAWoodenFishItem(3, R.drawable.example, 400,
                            "xf3", false, false));
                    writeInitialWoodenFishData(list);
                    resList.addAll(list);
                    resCode = VAItemEvent.RESPONSE_CODE_FAIL;
                }

                cursor = getDataStatementVAItemsSticker();
                if (cursor.moveToFirst()) {
                    do {
                        VAStickerItem item = new VAStickerItem();
                        @SuppressLint("Range") String name =
                                cursor.getString(cursor.getColumnIndex(VAStickerItem.COLUMN_NAME));
                        @SuppressLint("Range") int cost =
                                cursor.getInt(cursor.getColumnIndex(VAStickerItem.COLUMN_COST));
                        @SuppressLint("Range") int id =
                                cursor.getInt(cursor.getColumnIndex(VAStickerItem.COLUMN_ID));
                        @SuppressLint("Range") int resId =
                                cursor.getInt(cursor.getColumnIndex(VAStickerItem.COLUMN_RES_ID_1));
                        @SuppressLint("Range") int resId2 =
                                cursor.getInt(cursor.getColumnIndex(VAStickerItem.COLUMN_RES_ID_2));
                        @SuppressLint("Range") int isAcquired =
                                cursor.getInt(cursor.getColumnIndex(VAStickerItem.COLUMN_ACQUIRED));
                        @SuppressLint("Range") int isEquipped =
                                cursor.getInt(cursor.getColumnIndex(VAStickerItem.COLUMN_EQUIPPED));
                        item.name = name;
                        item.id = id;
                        item.cost = cost;
                        item.resId = resId;
                        item.resId2 = resId2;
                        item.isAcquired = isAcquired == 1;
                        item.isEquipped = isEquipped == 1;
                        resList.add(item);
                    } while (cursor.moveToNext());
                } else {//no data
                    ArrayList<VAStickerItem> list = new ArrayList<>();
                    list.add(new VAStickerItem(0, R.drawable.va_hammer,
                            R.drawable.va_hammer_down, 100, "hammer0",
                            false, false));
                    list.add(new VAStickerItem(1, R.drawable.va_hammer,
                            R.drawable.va_hammer_down, 200, "hammer1",
                            false, false));
                    list.add(new VAStickerItem(2, R.drawable.va_hammer,
                            R.drawable.va_hammer_down, 300, "hammer2",
                            false, false));
                    list.add(new VAStickerItem(3, R.drawable.va_hammer,
                            R.drawable.va_hammer_down, 400, "hammer3",
                            false, false));
                    writeInitialStickerData(list);
                    resList.addAll(list);
                    resCode = VAItemEvent.RESPONSE_CODE_FAIL;
                }

                cursor = getDataStatementVAItemsBackground();
                if (cursor.moveToFirst()) {
                    do {
                        VABackgroundItem item = new VABackgroundItem();
                        @SuppressLint("Range") String name =
                                cursor.getString(cursor.getColumnIndex(VABackgroundItem.COLUMN_NAME));
                        @SuppressLint("Range") int cost =
                                cursor.getInt(cursor.getColumnIndex(VABackgroundItem.COLUMN_COST));
                        @SuppressLint("Range") int id =
                                cursor.getInt(cursor.getColumnIndex(VABackgroundItem.COLUMN_ID));
                        @SuppressLint("Range") int resId =
                                cursor.getInt(cursor.getColumnIndex(VABackgroundItem.COLUMN_RES_ID_1));
                        @SuppressLint("Range") int isAcquired =
                                cursor.getInt(cursor.getColumnIndex(VABackgroundItem.COLUMN_ACQUIRED));
                        @SuppressLint("Range") int isEquipped =
                                cursor.getInt(cursor.getColumnIndex(VABackgroundItem.COLUMN_EQUIPPED));
                        item.name = name;
                        item.id = id;
                        item.cost = cost;
                        item.resId = resId;
                        item.isAcquired = isAcquired == 1;
                        item.isEquipped = isEquipped == 1;
                        resList.add(item);
                    } while (cursor.moveToNext());
                } else {//no data
                    ArrayList<VABackgroundItem> list = new ArrayList<>();
                    list.add(new VABackgroundItem(0, R.drawable.example, 100,
                            "bg0", false, false));
                    list.add(new VABackgroundItem(1, R.drawable.example, 200,
                            "bg1", false, false));
                    list.add(new VABackgroundItem(2, R.drawable.example, 300,
                            "bg2", false, false));
                    list.add(new VABackgroundItem(3, R.drawable.example, 400,
                            "bg3", false, false));
                    writeInitialBackgroundData(list);
                    resList.addAll(list);
                    resCode = VAItemEvent.RESPONSE_CODE_FAIL;
                }

                //send notify
                VAItemEvent event = new VAItemEvent(resCode, resList);
                EventBus.getDefault().postSticky(event);
                close();
            }
        }).start();
    }

    public void writeInitialWoodenFishData(ArrayList<VAWoodenFishItem> list) {
        database.beginTransaction();
        try {
            for (VAWoodenFishItem data : list) {
                ContentValues values = new ContentValues();
                values.put(VAWoodenFishItem.COLUMN_ID, data.id);
                values.put(VAWoodenFishItem.COLUMN_NAME, data.name);
                values.put(VAWoodenFishItem.COLUMN_RES_ID_1, data.resId);
                values.put(VAWoodenFishItem.COLUMN_COST, data.cost);
                values.put(VAWoodenFishItem.COLUMN_ACQUIRED, 0);
                values.put(VAWoodenFishItem.COLUMN_EQUIPPED, 0);
                database.insert(VAWoodenFishItem.TABLE_NAME, null, values);
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    public void writeInitialStickerData(ArrayList<VAStickerItem> list) {
        database.beginTransaction();
        try {
            for (VAStickerItem data : list) {
                ContentValues values = new ContentValues();
                values.put(VAStickerItem.COLUMN_ID, data.id);
                values.put(VAStickerItem.COLUMN_NAME, data.name);
                values.put(VAStickerItem.COLUMN_RES_ID_1, data.resId);
                values.put(VAStickerItem.COLUMN_RES_ID_2, data.resId2);
                values.put(VAStickerItem.COLUMN_COST, data.cost);
                values.put(VAStickerItem.COLUMN_ACQUIRED, 0);
                values.put(VAStickerItem.COLUMN_EQUIPPED, 0);
                database.insert(VAStickerItem.TABLE_NAME, null, values);
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    public void writeInitialBackgroundData(ArrayList<VABackgroundItem> list) {
        database.beginTransaction();
        try {
            for (VABackgroundItem data : list) {
                ContentValues values = new ContentValues();
                values.put(VABackgroundItem.COLUMN_ID, data.id);
                values.put(VABackgroundItem.COLUMN_NAME, data.name);
                values.put(VABackgroundItem.COLUMN_RES_ID_1, data.resId);
                values.put(VABackgroundItem.COLUMN_COST, data.cost);
                values.put(VABackgroundItem.COLUMN_ACQUIRED, 0);
                values.put(VABackgroundItem.COLUMN_EQUIPPED, 0);
                database.insert(VABackgroundItem.TABLE_NAME, null, values);
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    public void openDatabase() throws SQLException {
        dbHelper = DBHelper.getInstance(VaUtils.getGlobalContext());
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

    public Cursor getDataStatementVAModel() {
        return database.query(VAModel.TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor getDataStatementVAItemsWooden() {
        return database.query(VAWoodenFishItem.TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor getDataStatementVAItemsSticker() {
        return database.query(VAStickerItem.TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor getDataStatementVAItemsBackground() {
        return database.query(VABackgroundItem.TABLE_NAME, null, null, null, null, null, null);
    }

    public interface VAControllerCallback {
        int RESULT_TYPE_ALL_SAVE = 0;
        int RESULT_TYPE_COIN_MODIFY = 1;
        int RESULT_TYPE_POINT_MODIFY = 2;
        int RESULT_TYPE_ALL_GET = 3;
        void onDatabaseOperationComplete(int resType, int resCode);
    }

    public interface VAControllerItemsCallback {
        void onItemsGet(int resType, int resCode);
    }
}
