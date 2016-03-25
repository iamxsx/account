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
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.DummyVieportChangeListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.PreviewColumnChartView;

public class PreviewColumnChartActivity extends AppCompatActivity {

    @InjectView(R.id.columnChartView)
    ColumnChartView columnChartView;
    @InjectView(R.id.chart_preview)
    PreviewColumnChartView chartPreview;

    ColumnChartData columnChartData1;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    /**
     * Deep copy of data.
     */
    private ColumnChartData columnChartData2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_column_chart);
        ButterKnife.inject(this);

        generateDefaultData();
        initColumnChart();
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("柱形预览图");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void generateDefaultData() {
        int numSubcolumns = 1;
        int numColumns = 50;
        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue((float) Math.random() * 50f + 5, ChartUtils.pickColor()));
            }

            columns.add(new Column(values));
        }

        columnChartData1 = new ColumnChartData(columns);
        columnChartData1.setAxisXBottom(new Axis());
        columnChartData1.setAxisYLeft(new Axis().setHasLines(true));

        // prepare preview data, is better to use separate deep copy for preview chart.
        // set color to grey to make preview area more visible.
        columnChartData2 = new ColumnChartData(columnChartData1);
        for (Column column : columnChartData2.getColumns()) {
            for (SubcolumnValue value : column.getValues()) {
                value.setColor(ChartUtils.DEFAULT_DARKEN_COLOR);
            }
        }

    }

    private void initColumnChart() {
        columnChartView.setColumnChartData(columnChartData1);
        columnChartView.setZoomEnabled(false);
        columnChartView.setScrollEnabled(false);

        chartPreview.setColumnChartData(columnChartData2);
        chartPreview.setViewportChangeListener(new DummyVieportChangeListener());

        previewX(false);
    }

    private void previewX(boolean animate) {
        Viewport tempViewport = new Viewport(columnChartView.getMaximumViewport());
        float dx = tempViewport.width() / 4;
        tempViewport.inset(dx, 0);
        if (animate) {
            chartPreview.setCurrentViewportWithAnimation(tempViewport);
        } else {
            chartPreview.setCurrentViewport(tempViewport);
        }
        chartPreview.setZoomType(ZoomType.HORIZONTAL);
    }

}
