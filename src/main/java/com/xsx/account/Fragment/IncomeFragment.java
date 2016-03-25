package com.xsx.account.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dpizarro.uipicker.library.picker.PickerUI;
import com.dpizarro.uipicker.library.picker.PickerUISettings;
import com.xsx.account.R;
import com.xsx.account.bean.Account;
import com.xsx.account.bean.Bill;
import com.xsx.account.db.AccountDao;
import com.xsx.account.db.BillDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 收入
 */
public class IncomeFragment extends BaseFragment {


    @InjectView(R.id.et_sum)
    EditText etSum;
    @InjectView(R.id.ll_enter_money)
    LinearLayout llEnterMoney;
    @InjectView(R.id.tv_category)
    TextView tvCategory;
    @InjectView(R.id.tv_what)
    TextView tvWhat;
    @InjectView(R.id.ll_catagory)
    LinearLayout llCatagory;
    @InjectView(R.id.tv_account)
    TextView tvAccount;
    @InjectView(R.id.ll_account)
    LinearLayout llAccount;
    @InjectView(R.id.et_remarks)
    EditText etRemarks;
    @InjectView(R.id.btn_sure)
    Button btnSure;
    @InjectView(R.id.picker_ui_view1)
    PickerUI pickerUiView1;
    @InjectView(R.id.picker_ui_view2)
    PickerUI pickerUiView2;
    @InjectView(R.id.ll_picker)
    LinearLayout llPicker;
    @InjectView(R.id.cv1)
    CardView cv1;
    @InjectView(R.id.picker_ui_view3)
    PickerUI pickerUiView3;
    @InjectView(R.id.cv2)
    CardView cv2;
    private int currentPosition1 = -1;
    private int currentPosition2 = -1;
    private int currentPosition3 = -1;

    List<String> options;
    List<String> options2;

    private BillDao dao;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        dao = new BillDao(getContext());
    }

    private void initViews() {
        initPicker1();
        initPicker2();
        initPicker3();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    List<Account> accounts;

    private void initPicker3() {
        options2 = new ArrayList<>();
        accounts = new AccountDao(getContext()).selectAll();
        if (accounts.size() <= 0) {
            accounts.add(new Account("其他", 0));
        }
        for (Account account : accounts) {
            options2.add(account.getAccountName() + " ¥" + account.getSum());
        }
        pickerUiView3.setItems(getContext(), options2);
        pickerUiView3.setAutoDismiss(false);
        pickerUiView3.setOnClickItemPickerUIListener(new PickerUI.PickerUIItemClickListener() {
            @Override
            public void onItemClickPickerUI(int which, int position, String valueResult) {
                currentPosition3 = position;
                tvAccount.setText(valueResult);
            }
        });
        if (currentPosition3 == -1) {
            pickerUiView3.slide();
        } else {
            pickerUiView3.slide(currentPosition3);
        }
    }

    private void initPicker2() {
        pickerUiView2.setItems(getContext(), null);
        pickerUiView2.setItemsClickables(false);
        pickerUiView2.setAutoDismiss(false);
        pickerUiView2.setOnClickItemPickerUIListener(new PickerUI.PickerUIItemClickListener() {
            @Override
            public void onItemClickPickerUI(int which, int position, String valueResult) {
                currentPosition2 = position;
                tvWhat.setText(valueResult);
            }
        });
        if (currentPosition2 == -1) {
            pickerUiView2.slide();
        } else {
            pickerUiView2.slide(currentPosition2);
        }
    }

    private void initPicker1() {
        options = Arrays.asList(getResources().getStringArray(R.array.a3));
        PickerUISettings pickerUISettings = new PickerUISettings.Builder()
                .withItems(options)
                .withAutoDismiss(false)
                .withItemsClickables(false)
                .withUseBlur(false)
                .build();

        pickerUiView1.setSettings(pickerUISettings);

        pickerUiView1.setOnClickItemPickerUIListener(new PickerUI.PickerUIItemClickListener() {
            @Override
            public void onItemClickPickerUI(int which, int position, String valueResult) {
                currentPosition1 = position;
                tvCategory.setText(valueResult);
                List<String> options = null;
                switch (position) {
                    case 0:
                        options = Arrays.asList(getResources().getStringArray(R.array.a3_1));
                        break;
                    case 1:
                        options = Arrays.asList(getResources().getStringArray(R.array.a3_2));
                        break;
                }
                pickerUiView2.setItems(getContext(), options);

            }
        });

        if (currentPosition1 == -1) {
            pickerUiView1.slide();
        } else {
            pickerUiView1.slide(currentPosition1);
        }

    }

    @OnClick({R.id.ll_catagory, R.id.ll_account, R.id.btn_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_catagory:
                showCategoryPicker();
                break;
            case R.id.ll_account:
                showAccountPicker();
                break;
            case R.id.btn_sure:
                save();
                break;
        }
    }

    private void save() {
        String sum = etSum.getText().toString();
        if (TextUtils.isEmpty(sum)) {
            showToast("金额不能为空");
            return;
        }
        String category = tvCategory.getText().toString();
        if (TextUtils.isEmpty(category)) {
            showToast("分类不能为空");
            return;
        }
        String what = tvWhat.getText().toString();
        if (TextUtils.isEmpty(what)) {
            showToast("分类不能为空");
            return;
        }
        String account = tvAccount.getText().toString();
        if (TextUtils.isEmpty(account)) {
            showToast("账户不能为空");
            return;
        }
        String date = getCurrentDate();
        String time = getCurrentTime();
        String remarks = etRemarks.getText().toString();
        Bill bill = new Bill("收入", date, time, Float.parseFloat(sum), category, what, account, remarks);
        //为选择的账户加上相应的金额
        if (dao.insert(bill)) {
            Account account1 = accounts.get(currentPosition3);
            account1.setSum(account1.getSum() + Float.parseFloat(sum));
            new AccountDao(getContext()).update(account1);
            showToast("记录成功，请在主界面下拉刷新一下哦~~~");
            getActivity().finish();
        }
    }


    private void showAccountPicker() {
        cv1.setVisibility(View.GONE);
        if (cv2.getVisibility() == View.GONE) {
            cv2.setVisibility(View.VISIBLE);
        } else {
            cv2.setVisibility(View.GONE);
        }
    }

    private void showCategoryPicker() {
        cv2.setVisibility(View.GONE);
        if (cv1.getVisibility() == View.GONE) {
            cv1.setVisibility(View.VISIBLE);
        } else {
            cv1.setVisibility(View.GONE);
        }
    }


}
