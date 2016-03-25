package com.xsx.account.Fragment;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by XSX on 2016/3/14.
 */
public class BaseFragment extends Fragment {
    private SimpleDateFormat sdf;
    private Calendar calendar;
    protected void showToast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    protected String getCurrentTime() {
        calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }

    protected String getCurrentDate() {
        calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    protected void showDialog(){

    }
}
