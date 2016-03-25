package com.xsx.account.Fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xsx.account.R;
import com.xsx.account.bean.Bill;
import com.xsx.account.db.BillDao;
import com.xsx.account.ui.AccountingActivity;
import com.xsx.account.ui.BillDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 流水线界面
 */
public class PipelineFragment extends BaseFragment {


    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.tv_income_total)
    TextView tvIncomeTotal;
    @InjectView(R.id.btn_add)
    Button btnAdd;
    @InjectView(R.id.tv_expense_total)
    TextView tvExpenseTotal;
    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private List<Bill> bills;
    private BillDao dao;
    MyAdapter adapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDatas();
        initViews();
    }

    private void initDatas() {
        bills = new ArrayList<>();
        dao = new BillDao(getContext());
        bills = dao.selectAll();
        tvExpenseTotal.setText(dao.getExpenseSum() + "");
        tvIncomeTotal.setText(dao.getIncomeSum() + "");
    }


    private void initViews() {
        adapter = new MyAdapter(bills);
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        swipeRefreshLayout.setColorSchemeResources(R.color.common_green, R.color.text_color_red);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                swipeRefreshLayout.setRefreshing(false);
            }

        });
    }

    private void refresh() {
        bills = dao.selectAll();
        tvExpenseTotal.setText(dao.getExpenseSum() + "");
        tvIncomeTotal.setText(dao.getIncomeSum() + "");
        adapter = new MyAdapter(bills);
        recyclerview.setAdapter(adapter);

    }

    @OnClick({R.id.btn_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                enterAccountingActivity();
                break;

        }
    }

    private void enterAccountingActivity() {
        startActivity(new Intent(getContext(), AccountingActivity.class));
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        List<Bill> bills;

        public MyAdapter(List<Bill> bills) {
            this.bills = bills;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if (viewType == 0) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pipeline_left, parent, false);
            } else {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pipeline_right, parent, false);
            }
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            final Bill bill = bills.get(position);
            String category = bill.getCategory();
            int type = getItemViewType(position);
            int id = getImageId(category, type);
            holder.ivWhat.setImageResource(id);
            holder.tvWhat.setText(bill.getWhat());
            holder.tvSum.setText(bill.getSum() + "");
            holder.tvDate.setText(bill.getDate());
            holder.tvTime.setText(bill.getTime());
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), BillDetailActivity.class);
                    intent.putExtra("bill", bills.get(position));
                    startActivity(intent);
                }
            });
            holder.view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("是否删除该账单？");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dao.delete(bill.getId());
                            refresh();
                            showToast("删除成功");
                        }
                    });
                    builder.show();
                    return false;
                }
            });

        }

        @Override
        public int getItemCount() {
            return bills.size();
        }

        @Override
        public int getItemViewType(int position) {
            String type = bills.get(position).getType();
            if (type.equals("收入")) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    private int getImageId(String category, int type) {
        int id = R.drawable.ic_others;
        if (type == 1) {
            switch (category) {
                case "食品酒水":
                    id = R.drawable.ic_food;
                    break;
                case "衣服饰品":
                    id = R.drawable.ic_clothes;
                    break;
                case "居家行业":
                    id = R.drawable.ic_house;
                    break;
                case "行车交通":
                    id = R.drawable.ic_car;
                    break;
                case "交流通讯":
                    id = R.drawable.ic_phone;
                    break;
                case "休闲娱乐":
                    id = R.drawable.ic_game;
                    break;
                case "学习进修":
                    id = R.drawable.ic_book;
                    break;
                case "人情往来":
                    id = R.drawable.ic_meeting;
                    break;
                case "医疗保健":
                    id = R.drawable.ic_ill;
                    break;
                case "金融投资":
                    id = R.drawable.ic_investment;
                    break;
                case "其他":
                    id = R.drawable.ic_others;
                    break;
            }
        } else {
            switch (category) {
                case "职业收入":
                    id = R.drawable.ic_income;
                    break;
                case "其他收入":
                    id = R.drawable.ic_others;
                    break;
            }
        }
        return id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pipeline, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ImageView ivWhat;
        public TextView tvWhat;
        public TextView tvSum;
        public TextView tvDate;
        public TextView tvTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ivWhat = (ImageView) view.findViewById(R.id.iv_what);
            tvSum = (TextView) view.findViewById(R.id.tv_sum);
            tvWhat = (TextView) view.findViewById(R.id.tv_what);
            tvDate = (TextView) view.findViewById(R.id.tv_date);
            tvTime = (TextView) view.findViewById(R.id.tv_time);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
