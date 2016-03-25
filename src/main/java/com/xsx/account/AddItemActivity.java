package com.xsx.account;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dpizarro.uipicker.library.picker.PickerUI;
import com.dpizarro.uipicker.library.picker.PickerUISettings;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class AddItemActivity extends AppCompatActivity {
    @InjectView(R.id.ll_enter_money)
    LinearLayout llEnterMoney;
    @InjectView(R.id.ll_catagory)
    LinearLayout llCatagory;
    @InjectView(R.id.picker_ui_view1)
    PickerUI mPickerUI1;
    @InjectView(R.id.picker_ui_view2)
    PickerUI mPickerUI2;
    @InjectView(R.id.tv_category)
    TextView tvCategory;
    @InjectView(R.id.tv_what)
    TextView tvWhat;
    @InjectView(R.id.ll_picker)
    LinearLayout llPicker;
    @InjectView(R.id.tv_account)
    TextView tvAccount;
    @InjectView(R.id.tv_where_money)
    TextView tvWhereMoney;
    @InjectView(R.id.ll_account)
    LinearLayout llAccount;
    @InjectView(R.id.picker_ui_view3)
    PickerUI pickerUiView3;
    @InjectView(R.id.picker_ui_view4)
    PickerUI pickerUiView4;
    @InjectView(R.id.ll_picker2)
    LinearLayout llPicker2;

    private int currentPosition1 = -1;
    private int currentPosition2 = -1;
    private int currentPosition3 = -1;
    private int currentPosition4 = -1;

    List<String> options;
    List<String> options2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        ButterKnife.inject(this);
        initViews();
    }

    private void initViews() {
        initPicker1();
        initPicker2();
        initPicker3();
        initPicker4();
    }

    private void initPicker4() {
        pickerUiView4.setItems(this, null);
        pickerUiView4.setAutoDismiss(false);
        pickerUiView4.setOnClickItemPickerUIListener(new PickerUI.PickerUIItemClickListener() {
            @Override
            public void onItemClickPickerUI(int which, int position, String valueResult) {
                currentPosition4 = position;
                tvWhereMoney.setText(valueResult);
            }
        });
        if (currentPosition4 == -1) {
            pickerUiView4.slide();
        } else {
            pickerUiView4.slide(currentPosition4);
        }
    }


    private void initPicker3() {
        options2 = Arrays.asList(getResources().getStringArray(R.array.a2));
        pickerUiView3.setItems(this, options2);
        pickerUiView3.setAutoDismiss(false);
        pickerUiView3.setOnClickItemPickerUIListener(new PickerUI.PickerUIItemClickListener() {
            @Override
            public void onItemClickPickerUI(int which, int position, String valueResult) {
                currentPosition3 = position;
                tvAccount.setText(valueResult);
                List<String> options = null;
                switch (position) {
                    case 0:
                        options = Arrays.asList(getResources().getStringArray(R.array.a2_1));
                        break;
                    case 1:
                        options = Arrays.asList(getResources().getStringArray(R.array.a2_2));
                        break;
                    case 2:
                        options = Arrays.asList(getResources().getStringArray(R.array.a2_3));
                        break;
                }
                pickerUiView4.setItems(AddItemActivity.this, options);
            }
        });
        if (currentPosition3 == -1) {
            pickerUiView3.slide();
        } else {
            pickerUiView3.slide(currentPosition3);
        }
    }

    private void initPicker2() {
        mPickerUI2.setItems(this, null);
        mPickerUI2.setItemsClickables(false);
        mPickerUI2.setAutoDismiss(false);
        mPickerUI2.setOnClickItemPickerUIListener(new PickerUI.PickerUIItemClickListener() {
            @Override
            public void onItemClickPickerUI(int which, int position, String valueResult) {
                currentPosition2 = position;
                tvWhat.setText(valueResult);
            }
        });
        if (currentPosition2 == -1) {
            mPickerUI2.slide();
        } else {
            mPickerUI2.slide(currentPosition2);
        }
    }

    private void initPicker1() {
        options = Arrays.asList(getResources().getStringArray(R.array.a1));
        PickerUISettings pickerUISettings = new PickerUISettings.Builder()
                .withItems(options)
                .withAutoDismiss(false)
                .withItemsClickables(false)
                .withUseBlur(false)
                .build();

        mPickerUI1.setSettings(pickerUISettings);

        mPickerUI1.setOnClickItemPickerUIListener(new PickerUI.PickerUIItemClickListener() {
            @Override
            public void onItemClickPickerUI(int which, int position, String valueResult) {
                currentPosition1 = position;
                tvCategory.setText(valueResult);
                List<String> options = null;
                switch (position) {
                    case 0:
                        options = Arrays.asList(getResources().getStringArray(R.array.a1_1));
                        break;
                    case 1:
                        options = Arrays.asList(getResources().getStringArray(R.array.a1_2));
                        break;
                    case 2:
                        options = Arrays.asList(getResources().getStringArray(R.array.a1_3));
                        break;
                    case 3:
                        options = Arrays.asList(getResources().getStringArray(R.array.a1_4));
                        break;
                    case 4:
                        options = Arrays.asList(getResources().getStringArray(R.array.a1_5));
                        break;
                    case 5:
                        options = Arrays.asList(getResources().getStringArray(R.array.a1_6));
                        break;
                    case 6:
                        options = Arrays.asList(getResources().getStringArray(R.array.a1_7));
                        break;
                    case 7:
                        options = Arrays.asList(getResources().getStringArray(R.array.a1_8));
                        break;
                    case 8:
                        options = Arrays.asList(getResources().getStringArray(R.array.a1_9));
                        break;
                    case 9:
                        options = Arrays.asList(getResources().getStringArray(R.array.a1_10));
                        break;
                    case 10:
                        options = Arrays.asList(getResources().getStringArray(R.array.a1_11));
                        break;
                }
                mPickerUI2.setItems(AddItemActivity.this, options);

            }
        });

        if (currentPosition1 == -1) {
            mPickerUI1.slide();
        } else {
            mPickerUI1.slide(currentPosition1);
        }

    }

    @OnClick({R.id.ll_catagory, R.id.ll_account})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_catagory:
                showCategoryPicker();
                break;
            case R.id.ll_account:
                showAccountPicker();
                break;
        }
    }

    private void showAccountPicker() {
        llPicker.setVisibility(View.GONE);
        if (llPicker2.getVisibility() == View.GONE) {
            llPicker2.setVisibility(View.VISIBLE);
        } else {
            llPicker2.setVisibility(View.GONE);
        }
    }

    private void showCategoryPicker() {
        llPicker2.setVisibility(View.GONE);
        if (llPicker.getVisibility() == View.GONE) {
            llPicker.setVisibility(View.VISIBLE);
        } else {
            llPicker.setVisibility(View.GONE);
        }

    }
}
