package com.xsx.account.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by XSX on 2016/3/13.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DBNAME = "ACCOUNT_DATABASE";
    private static final int VERSION = 2;
    private static DBHelper mInstance;

    private static final String TABLE_BILL = "bill";
    private static final String TABLE_ACCOUNT = "account";


    private static final String CREATE_TABLE_BILL = "" +
            "CREATE TABLE " + TABLE_BILL + "(" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "TYPE VARCHAR(10)," +
            "SUM decimal(10,2)," +
            "DATE VARCHAR(20)," +
            "TIME VARCHAR(20)," +
            "CATEGORY VARCHAR(20)," +
            "WHAT VARCHAR(20)," +
            "ACCOUNT VARCHAR(20)," +
            "WHERES VARCHAR(20)," +
            "REMARKS VARCHAR(20)" +
            ")";



    private static final String CREATE_TABLE_ACCOUNT=""+
            "CREATE TABLE " + TABLE_ACCOUNT + "(" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "ACCOUNT_NAME VARCHAR(20)," +
            "SUM decimal(10,2)" +
            ")";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DBHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    public static DBHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBHelper.class) {
                if (mInstance == null) {
                    mInstance = new DBHelper(context);
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BILL);
        db.execSQL(CREATE_TABLE_ACCOUNT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
    }
}
