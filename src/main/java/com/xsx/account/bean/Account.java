package com.xsx.account.bean;

/**
 * Created by XSX on 2016/3/14.
 */
public class Account {

    private int id;
    private String accountName;
    private float sum;

    public Account() {
    }

    public Account(String accountName, float sum) {
        this.accountName = accountName;
        this.sum = sum;
    }

    public Account(int id, String accountName, float sum) {
        this.id = id;
        this.accountName = accountName;
        this.sum = sum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }
}
