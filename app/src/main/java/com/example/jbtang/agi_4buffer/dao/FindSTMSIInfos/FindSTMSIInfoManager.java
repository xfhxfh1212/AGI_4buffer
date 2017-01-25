package com.example.jbtang.agi_4buffer.dao.FindSTMSIInfos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jbtang.agi_4buffer.service.FindSTMSI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ai on 16/6/14.
 */
public class FindSTMSIInfoManager {
    private SQLiteOpenHelper helper;
    private SQLiteDatabase db;

    public FindSTMSIInfoManager(Context context) {
        helper = new FindSTMSIInfoDBHelper(context);
        db = helper.getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS " + FindSTMSIInfoDBHelper.TABLE_NAME +
                "(stmsi TEXT PRIMARY KEY, count TEXT, time TEXT, pci TEXT, earfcn TEXT, ecgi TEXT, doubtful TEXT)");
    }

    public void add(List<FindSTMSI.CountSortedInfo> cellInfos) {
        db.beginTransaction();
        try {
            for (FindSTMSI.CountSortedInfo countSortedInfo : cellInfos) {
                db.execSQL("INSERT INTO " + FindSTMSIInfoDBHelper.TABLE_NAME + " VALUES(?, ?, ?, ?, ?, ?, ?)",
                        new Object[]{countSortedInfo.stmsi, countSortedInfo.count, countSortedInfo.time, countSortedInfo.pci, countSortedInfo.earfcn, countSortedInfo.ecgi, countSortedInfo.doubtful?"true":"false"});
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void insert(FindeSTMSIInfoDAO dao) {
        db.execSQL("INSERT INTO " + FindSTMSIInfoDBHelper.TABLE_NAME + "VALUES(?,?,?,?,?,?,?)",
                new Object[]{dao.stmsi, dao.count, dao.time, dao.pci, dao.earfcn, dao.ecgi,dao.doubtful});
    }

    public List<FindeSTMSIInfoDAO> listDB() {
        String sql = "SELECT * FROM " + FindSTMSIInfoDBHelper.TABLE_NAME;
        final Cursor c = db.rawQuery(sql, new String[]{});
        List<FindeSTMSIInfoDAO> findeSTMSIInfoDAOs = new ArrayList<>();
        while (c.moveToNext()) {
            String stmsi = c.getString(c.getColumnIndex("stmsi"));
            String count = c.getString(c.getColumnIndex("count"));
            String time = c.getString(c.getColumnIndex("time"));
            String pci = c.getString(c.getColumnIndex("pci"));
            String earfcn = c.getString(c.getColumnIndex("earfcn"));
            String ecgi = c.getString(c.getColumnIndex("ecgi"));
            String doubtful = c.getString(c.getColumnIndex("doubtful"));
            FindeSTMSIInfoDAO findeSTMSIInfoDAO = new FindeSTMSIInfoDAO(stmsi, count, time, pci, earfcn, ecgi, doubtful);
            findeSTMSIInfoDAOs.add(findeSTMSIInfoDAO);
        }
        c.close();
        return findeSTMSIInfoDAOs;
    }

    public void clear() {
        db.execSQL("DELETE FROM " + FindSTMSIInfoDBHelper.TABLE_NAME);
    }

    public void closeDB() {
        db.close();
    }
}
