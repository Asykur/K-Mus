package com.example.asykur.kmus.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.asykur.kmus.Pojo.DataKamus;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.asykur.kmus.Database.DBContract.KamusColumns.ARTI;
import static com.example.asykur.kmus.Database.DBContract.KamusColumns.KATA;
import static com.example.asykur.kmus.Database.DBContract.TABLE_EN_IDN;
import static com.example.asykur.kmus.Database.DBContract.TABLE_IDN_EN;

public class DBKamusHelper {

    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public DBKamusHelper(Context context) {
        this.context = context;
    }

    public DBKamusHelper open() throws SQLException {
        dbHelper = new DBHelper(context);
        database = dbHelper.getReadableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    //mencari arti berdasarkan ID
    public ArrayList<DataKamus> getArtibyKataEN(String kata) {

        Cursor cursor = database.query(TABLE_EN_IDN, null, KATA + " LIKE ?", new String[]{kata}, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<DataKamus> listData = new ArrayList<>();
        DataKamus dataKamus;
        if (cursor.getCount() > 0) {
            do {
                dataKamus = new DataKamus();
                dataKamus.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                dataKamus.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
                dataKamus.setArti(cursor.getString(cursor.getColumnIndexOrThrow(ARTI)));

                listData.add(dataKamus);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return listData;
    }

    public ArrayList<DataKamus> getArtibyKataIDN(String kata) {
        Cursor cursor = database.query(TABLE_IDN_EN, null, KATA + " LIKE ?", new String[]{kata}, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<DataKamus> listData = new ArrayList<>();
        DataKamus dataKamus;
        if (cursor.getCount() > 0) {
            do {
                dataKamus = new DataKamus();
                dataKamus.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                dataKamus.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
                dataKamus.setArti(cursor.getString(cursor.getColumnIndexOrThrow(ARTI)));

                listData.add(dataKamus);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return listData;
    }

    //get All data
    public ArrayList<DataKamus> getAllDataEN() {
        Cursor cursor = database.query(TABLE_EN_IDN, null, null, null, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<DataKamus> arrayList = new ArrayList<>();
        DataKamus dataKamus;
        if (cursor.getCount() > 0) {
            do {
                dataKamus = new DataKamus();
                dataKamus.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                dataKamus.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
                dataKamus.setArti(cursor.getString(cursor.getColumnIndexOrThrow(ARTI)));

                arrayList.add(dataKamus);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<DataKamus> getAllDataIDN() {
        Cursor cursor = database.query(TABLE_IDN_EN, null, null, null, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<DataKamus> arrayList = new ArrayList<>();
        DataKamus dataKamus;
        if (cursor.getCount() > 0) {
            do {
                dataKamus = new DataKamus();
                dataKamus.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                dataKamus.setKata(cursor.getString(cursor.getColumnIndexOrThrow(KATA)));
                dataKamus.setArti(cursor.getString(cursor.getColumnIndexOrThrow(ARTI)));

                arrayList.add(dataKamus);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    // Insert Data
    public long insert(DataKamus dataKamus) {
        ContentValues values = new ContentValues();
        values.put(KATA, dataKamus.getKata());
        values.put(ARTI, dataKamus.getArti());
        return database.insert(TABLE_EN_IDN, null, values);
    }

    // untuk memulai sesi query transaction
    public void beginTransaction() {
        database.beginTransaction();
    }


    // jika query transaction berhasil, jika error jangan panggil method ini
    public void setTransactionSuccess() {
        database.setTransactionSuccessful();
    }


    // untuk mengakhiri sesi query transaction
    public void endTransaction() {
        database.endTransaction();
    }


    // query insert di dalam transaction
    public void insertTransactionEN(DataKamus dataKamus) {
        String sqlEN = "INSERT INTO " + TABLE_EN_IDN + " (" + KATA + ", " + ARTI + ") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sqlEN);
        stmt.bindString(1, dataKamus.getKata());
        stmt.bindString(2, dataKamus.getArti());
        stmt.execute();
        stmt.clearBindings();

    }

    public void insertTransactionIDN(DataKamus dataKamus) {
        String sqlIDN = "INSERT INTO " + TABLE_IDN_EN + " (" + KATA + ", " + ARTI + ") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sqlIDN);
        stmt.bindString(1, dataKamus.getKata());
        stmt.bindString(2, dataKamus.getArti());
        stmt.execute();
        stmt.clearBindings();

    }

    //Update Data
    public int update(DataKamus dataKamus) {
        ContentValues values = new ContentValues();
        values.put(KATA, dataKamus.getKata());
        values.put(ARTI, dataKamus.getArti());
        return database.update(TABLE_EN_IDN, values, _ID + "='" + dataKamus.getId() + "'", null);
    }

    // Delete data
    public int delete(int id) {
        return database.delete(TABLE_EN_IDN, _ID + "='" + id + "'", null);
    }
}
