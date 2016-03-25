package com.xsx.account.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by XSX on 2016/3/13.
 */
public class Bill implements Parcelable {

    private int id;
    /**
     * 类型：支出、收入
     */
    private String type;
    /**
     * 日期
     */
    private String date;
    /**
     * 时间
     */
    private String time;
    /**
     * 金额
     */
    private float sum;
    /**
     * 种类
     */
    private String category;
    /**
     * 消费项
     */
    private String what;
    /**
     * 账户
     */
    private String account;
    /**
     * 备注
     */
    private String remarks;

    public Bill() {
    }

    public Bill(String type, String date, String time, float sum, String category, String what, String account, String remarks) {
        this.type = type;
        this.date = date;
        this.time = time;
        this.sum = sum;
        this.category = category;
        this.what = what;
        this.account = account;
        this.remarks = remarks;
    }

    public Bill(int id, String type, String date, String time, float sum, String category, String what, String account, String remarks) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.time = time;
        this.sum = sum;
        this.category = category;
        this.what = what;
        this.account = account;
        this.remarks = remarks;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }


    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", sum=" + sum +
                ", category='" + category + '\'' +
                ", what='" + what + '\'' +
                ", account='" + account + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.type);
        dest.writeString(this.date);
        dest.writeString(this.time);
        dest.writeFloat(this.sum);
        dest.writeString(this.category);
        dest.writeString(this.what);
        dest.writeString(this.account);
        dest.writeString(this.remarks);
    }

    protected Bill(Parcel in) {
        this.id = in.readInt();
        this.type = in.readString();
        this.date = in.readString();
        this.time = in.readString();
        this.sum = in.readFloat();
        this.category = in.readString();
        this.what = in.readString();
        this.account = in.readString();
        this.remarks = in.readString();
    }

    public static final Parcelable.Creator<Bill> CREATOR = new Parcelable.Creator<Bill>() {
        public Bill createFromParcel(Parcel source) {
            return new Bill(source);
        }

        public Bill[] newArray(int size) {
            return new Bill[size];
        }
    };
}
