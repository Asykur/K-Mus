package com.example.asykur.kmus.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.asykur.kmus.Database.DBContract.KamusColumns.ARTI;
import static com.example.asykur.kmus.Database.DBContract.KamusColumns.KATA;
import static com.example.asykur.kmus.Database.DBContract.TABLE_EN_IDN;
import static com.example.asykur.kmus.Database.DBContract.TABLE_IDN_EN;

public class DBHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbkamus";
    private static final int DATABASE_VERSION = 1;
    public static String CREATE_TABLE_KAMUS_EN_IDN = "create table " + TABLE_EN_IDN + "(" +
            _ID + " integer primary key autoincrement, " +
            KATA + " text not null, " +
            ARTI + " text not null); ";

    public static String CREATE_TABLE_KAMUS_IDN_EN = "create table " + TABLE_IDN_EN + "(" +
            _ID + " integer primary key autoincrement, " +
            KATA + " text not null, " +
            ARTI + " text not null); ";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_KAMUS_EN_IDN);
        sqLiteDatabase.execSQL(CREATE_TABLE_KAMUS_IDN_EN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EN_IDN);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_IDN_EN);
        onCreate(sqLiteDatabase);
    }
}
