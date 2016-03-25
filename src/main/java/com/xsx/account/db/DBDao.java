package com.xsx.account.db;

import java.util.List;

/**
 * Created by XSX on 2016/3/13.
 */
public interface DBDao<T> {

    public boolean insert(T t);

    public void delete(int id);

    public void update(T t);

    public T select(int id);

    public List<T> selectAll();

    public float getExpenseSum();

    public float getIncomeSum();


}
