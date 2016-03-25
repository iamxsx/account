package com.xsx.account.bean;

/**
 * 扇形图的一块
 * Created by XSX on 2016/3/15.
 */
public class PipeBean {
    /**
     * 百分比
     */
    private float percent;
    /**
     * 块名
     */
    private String name;

    public PipeBean() {
    }

    public PipeBean(float percent, String name) {
        this.percent = percent;
        this.name = name;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
