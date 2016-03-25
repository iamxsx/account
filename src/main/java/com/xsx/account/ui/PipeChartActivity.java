package com.xsx.account.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.xsx.account.R;
import com.xsx.account.bean.PipeBean;
import com.xsx.account.db.BillDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

public class PipeChartActivity extends AppCompatActivity {

    @InjectView(R.id.piechart_expense)
    PieChartView piechartExpense;
    @InjectView(R.id.piechart_income)
    PieChartView piechartIncome;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pipe_chart);
        ButterKnife.inject(this);
        initViews();
    }

    PieChartData data1;
    PieChartData data2;

    private void initViews() {
        initExpenseView();
        initIncomeView();
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitle("扇形图");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initIncomeView() {
        piechartIncome.setOnValueTouchListener(new ValueTouchListener());
        List<PipeBean> pipeBeens = new BillDao(this).getIncomeCategoryPercent();
        List<SliceValue> values = new ArrayList<SliceValue>();
        if (pipeBeens.size() <= 0) {
            SliceValue value = new SliceValue(100, Color.GRAY);
            value.setLabel("暂无收入");
            values.add(value);
        } else {
            for (int i = 0; i < pipeBeens.size(); i++) {
                PipeBean bean = pipeBeens.get(i);
                SliceValue sliceValue = new SliceValue(bean.getPercent(), ChartUtils.pickColor());
                sliceValue.setLabel(bean.getName());
                values.add(sliceValue);
            }
        }
        data2 = new PieChartData(values);
        data2.setHasLabels(true);
        data2.setHasLabelsOnlyForSelected(false);
        data2.setHasCenterCircle(true);
        data2.setCenterText1("收入扇形图");
        data2.setCenterText1Color(R.color.text_color_gray);
        data2.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                (int) getResources().getDimension(R.dimen.pie_chart_text1_size)));
        piechartIncome.setPieChartData(data2);
    }

    private void initExpenseView() {

        piechartExpense.setOnValueTouchListener(new ValueTouchListener());
        List<PipeBean> pipeBeens = new BillDao(this).getCategoryPercent();
        List<SliceValue> values = new ArrayList<SliceValue>();
        if (pipeBeens.size() <= 0) {
            SliceValue value = new SliceValue(100, Color.GRAY);
            value.setLabel("暂未消费");
            values.add(value);
        } else {
            for (int i = 0; i < pipeBeens.size(); i++) {
                PipeBean bean = pipeBeens.get(i);
                SliceValue sliceValue = new SliceValue(bean.getPercent(), ChartUtils.pickColor());
                sliceValue.setLabel(bean.getName());
                values.add(sliceValue);
            }
        }
        data1 = new PieChartData(values);
        data1.setHasLabels(true);
        data1.setHasLabelsOnlyForSelected(false);
        data1.setHasCenterCircle(true);
        data1.setCenterText1("消费扇形图");
        data1.setCenterText1Color(R.color.text_color_gray);
        data1.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                (int) getResources().getDimension(R.dimen.pie_chart_text1_size)));
        piechartExpense.setPieChartData(data1);
    }

    private class ValueTouchListener implements PieChartOnValueSelectListener {

        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {
            Toast.makeText(PipeChartActivity.this, "此项占：" + value.getValue() + "%", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onValueDeselected() {

        }

    }
}
