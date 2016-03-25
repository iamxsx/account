package com.xsx.account.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xsx.account.bean.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XSX on 2016/3/14.
 */
public class AccountDao implements DBDao<Account> {

    private static DBHelper helper;
    private SQLiteDatabase database;

    public AccountDao(Context context) {
        helper = DBHelper.getInstance(context);
    }

    @Override
    public boolean insert(Account account) {
        boolean success = false;
        database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ACCOUNT_NAME", account.getAccountName());
        values.put("SUM", account.getSum());
        long result = database.insert("account", null, values);
        if (result != -1) {
            success = true;
        }
        return success;
    }

    @Override
    public void delete(int id) {
        database = helper.getWritableDatabase();
        String whereClause = "ID=?";
        String[] whereArgs = new String[]{
                String.valueOf(id)
        };
        database.delete("account", whereClause, whereArgs);
    }

    @Override
    public void update(Account account) {
        database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ACCOUNT_NAME", account.getAccountName());
        values.put("SUM", account.getSum());
        String whereClause = "ID=?";
        String[] whereArgs = new String[]{
                String.valueOf(account.getId())
        };
        database.update("account", values, whereClause, whereArgs);
    }

    @Override
    public Account select(int id) {
        database = helper.getWritableDatabase();
        String sql = "select * from account where id=" + id;
        Account account = null;
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            cursor.move(0);
            int eid = cursor.getInt(cursor.getColumnIndex("ID"));
            float sum = cursor.getFloat(cursor.getColumnIndex("SUM"));
            String account_name = cursor.getString(cursor.getColumnIndex("ACCOUNT_NAME"));
            account = new Account(eid, account_name, sum);
        }
        return account;
    }

    @Override
    public List<Account> selectAll() {
        database = helper.getWritableDatabase();
        List<Account> accounts = new ArrayList<>();
        String sql = "select * from account";
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            float sum = cursor.getFloat(cursor.getColumnIndex("SUM"));
            String account_name = cursor.getString(cursor.getColumnIndex("ACCOUNT_NAME"));
            accounts.add(new Account(id, account_name, sum));
        }
        return accounts;
    }

    public float getTotalSum() {
        database = helper.getWritableDatabase();
        float totalSum=0;
        String sql = "select sum(sum) from account";
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            totalSum = cursor.getFloat(0);
        }
        return totalSum;
    }

    @Override
    public float getExpenseSum() {
        return 0;
    }

    @Override
    public float getIncomeSum() {
        return 0;
    }
}
