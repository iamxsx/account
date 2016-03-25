package com.xsx.account.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xsx.account.R;
import com.xsx.account.ui.LineChartActivity;
import com.xsx.account.ui.PipeChartActivity;
import com.xsx.account.ui.PreviewColumnChartActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 图表界面
 */
public class ChartFragment extends Fragment {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.ll_pipe})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_pipe:
                startActivity(new Intent(getContext(), PipeChartActivity.class));
                break;
        }
    }
}
