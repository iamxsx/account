package com.xsx.account.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xsx.account.bean.Bill;
import com.xsx.account.bean.PipeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XSX on 2016/3/13.
 */
public class BillDao implements DBDao<Bill> {

    private static DBHelper helper;
    private SQLiteDatabase database;

    public BillDao(Context context) {
        helper = DBHelper.getInstance(context);
    }

    @Override
    public boolean insert(Bill bill) {
        boolean success = false;
        database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TYPE", bill.getType());
        values.put("SUM", bill.getSum());
        values.put("DATE", bill.getDate());
        values.put("TIME", bill.getTime());
        values.put("CATEGORY", bill.getCategory());
        values.put("WHAT", bill.getWhat());
        values.put("ACCOUNT", bill.getAccount());
        values.put("REMARKS", bill.getRemarks());
        long result = database.insert("bill", null, values);
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
        database.delete("bill", whereClause, whereArgs);

    }

    @Override
    public void update(Bill bill) {
        database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TYPE", bill.getType());
        values.put("SUM", bill.getSum());
        values.put("DATE", bill.getDate());
        values.put("TIME", bill.getTime());
        values.put("CATEGORY", bill.getCategory());
        values.put("WHAT", bill.getWhat());
        values.put("ACCOUNT", bill.getAccount());
        values.put("REMARKS", bill.getRemarks());
        String whereClause = "ID=?";
        String[] whereArgs = new String[]{
                String.valueOf(bill.getId())
        };
        database.update("bill", values, whereClause, whereArgs);
    }

    @Override
    public Bill select(int id) {
        database = helper.getWritableDatabase();
        String sql = "select * from bill where id=" + id;
        Bill bill = null;
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            cursor.move(0);
            int eid = cursor.getInt(cursor.getColumnIndex("ID"));
            float sum = cursor.getFloat(cursor.getColumnIndex("SUM"));
            String date = cursor.getString(cursor.getColumnIndex("DATE"));
            String type = cursor.getString(cursor.getColumnIndex("TYPE"));
            String time = cursor.getString(cursor.getColumnIndex("TIME"));
            String category = cursor.getString(cursor.getColumnIndex("CATEGORY"));
            String what = cursor.getString(cursor.getColumnIndex("WHAT"));
            String account = cursor.getString(cursor.getColumnIndex("ACCOUNT"));
            String remarks = cursor.getString(cursor.getColumnIndex("REMARKS"));
            bill = new Bill(eid, type, date, time, sum, category, what, account, remarks);
        }
        return bill;
    }

    @Override
    public List<Bill> selectAll() {
        database = helper.getWritableDatabase();
        List<Bill> bills = new ArrayList<>();
        String sql = "select * from bill order by ID desc";
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String type = cursor.getString(cursor.getColumnIndex("TYPE"));
            float sum = cursor.getFloat(cursor.getColumnIndex("SUM"));
            String date = cursor.getString(cursor.getColumnIndex("DATE"));
            String time = cursor.getString(cursor.getColumnIndex("TIME"));
            String category = cursor.getString(cursor.getColumnIndex("CATEGORY"));
            String what = cursor.getString(cursor.getColumnIndex("WHAT"));
            String account = cursor.getString(cursor.getColumnIndex("ACCOUNT"));
            String remarks = cursor.getString(cursor.getColumnIndex("REMARKS"));
            bills.add(new Bill(id, type, date, time, sum, category, what, account, remarks));
        }
        return bills;
    }

    /**
     * select sum(sum),category from bill where type='支出' group by category
     * select sum(sum) from bill where type='支出'
     * 得到消费种类的数量，用于图表统计
     * <p/>
     * count=1,category=人情往来,
     * count=1,category=学习进修
     * count=2,category=职业收入
     * count=1,category=行车交通
     *
     * @return
     */
    public List<PipeBean> getCategoryPercent() {
        int total = getTotalExpense();
        List<PipeBean> pipeBeens = new ArrayList<>();
        database = helper.getWritableDatabase();
        String sql = "select sum(sum),category from bill where type='支出' group by category";
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int count = cursor.getInt(0);
            String catogory = cursor.getString(1);
            Log.i("xsx", "count=" + count + ",category=" + catogory + ",total=" + total);

            pipeBeens.add(new PipeBean((float) count * 100 / total, catogory));
        }
        return pipeBeens;
    }

    public int getTotalExpense() {
        int count = 0;
        database = helper.getWritableDatabase();
        String sql = "select sum(sum) from bill where type='支出'";
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            count = cursor.getInt(0);
        }
        return count;
    }

    public List<PipeBean> getIncomeCategoryPercent() {
        int total = getTotalIncome();
        List<PipeBean> pipeBeens = new ArrayList<>();
        database = helper.getWritableDatabase();
        String sql = "select sum(sum),category from bill where type='收入' group by category";
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int count = cursor.getInt(0);
            String catogory = cursor.getString(1);
            Log.i("xsx", "count=" + count + ",category=" + catogory + ",total=" + total);
            pipeBeens.add(new PipeBean((float) count * 100 / total, catogory));
        }
        return pipeBeens;
    }

    public int getTotalIncome() {
        int count = 0;
        database = helper.getWritableDatabase();
        String sql = "select sum(sum) from bill where type='收入'";
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            count = cursor.getInt(0);
        }
        return count;
    }

    @Override
    public float getExpenseSum() {
        database = helper.getWritableDatabase();
        String sql = "select sum(sum) from bill where type='支出'";
        Cursor cursor = database.rawQuery(sql, null);
        float sum = 0;
        if (cursor.moveToNext()) {
            sum = cursor.getFloat(0);
        }
        return sum;
    }

    @Override
    public float getIncomeSum() {
        database = helper.getWritableDatabase();
        String sql = "select sum(sum) from bill where type='收入'";
        Cursor cursor = database.rawQuery(sql, null);
        float sum = 0;
        if (cursor.moveToNext()) {
            sum = cursor.getFloat(0);
        }
        return sum;
    }
}
