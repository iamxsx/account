package com.xsx.account.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.xsx.account.R;
import com.xsx.account.bean.Bill;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BillDetailActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.et_sum)
    TextView etSum;
    @InjectView(R.id.tv_category)
    TextView tvCategory;
    @InjectView(R.id.tv_what)
    TextView tvWhat;
    @InjectView(R.id.tv_account)
    TextView tvAccount;
    @InjectView(R.id.et_remarks)
    TextView etRemarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);
        ButterKnife.inject(this);
        initViews();
    }

    private void initViews() {
        toolbar.setTitle("账单详情");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Bill bill = getIntent().getParcelableExtra("bill");
        etSum.setText(bill.getSum() + "");
        tvCategory.setText(bill.getCategory());
        tvWhat.setText(bill.getWhat());
        tvAccount.setText(bill.getAccount());
        etRemarks.setText(bill.getRemarks());
    }
}
