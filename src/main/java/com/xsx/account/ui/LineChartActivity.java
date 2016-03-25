package com.xsx.account.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.xsx.account.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lecho.lib.hellocharts.listener.DummyLineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class LineChartActivity extends AppCompatActivity {

    @InjectView(R.id.linechart)
    LineChartView linechart;
    LineChartData lineChartData;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private int numberOfLines = 2;
    private int maxNumberOfLines = 4;
    private int numberOfPoints = 30;
    float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        ButterKnife.inject(this);
        initViews();
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitle("折线图");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void generateValues() {
        for (int i = 0; i < maxNumberOfLines; ++i) {
            for (int j = 0; j < numberOfPoints; ++j) {
                randomNumbersTab[i][j] = (float) Math.random() * 100f;
            }
        }
    }

    private void initViews() {
        generateValues();
        linechart.setOnValueTouchListener(new DummyLineChartOnValueSelectListener());
        List<Line> lines = new ArrayList<Line>();
        for (int i = 0; i < numberOfLines; ++i) {

            List<PointValue> values = new ArrayList<PointValue>();
            for (int j = 0; j < numberOfPoints; ++j) {
                values.add(new PointValue(j, randomNumbersTab[i][j]));
            }

            Line line = new Line(values);
            line.setColor(getResources().getColor(R.color.common_green));
            //line.setCubic(true);
            line.setHasLines(true);
            line.setHasPoints(true);
            line.setPointColor(getResources().getColor(R.color.common_green));
            lines.add(line);
        }

        lineChartData = new LineChartData(lines);

        Axis axisX = new Axis();
        Axis axisY = new Axis().setHasLines(true);
        axisX.setName("日期");
        axisY.setName("");
        lineChartData.setAxisXBottom(axisX);
        lineChartData.setAxisYLeft(axisY);


        lineChartData.setBaseValue(Float.NEGATIVE_INFINITY);
        linechart.setLineChartData(lineChartData);
    }
}
