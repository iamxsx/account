package com.xsx.account.Fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xsx.account.R;
import com.xsx.account.bean.Account;
import com.xsx.account.db.AccountDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class WalletFragment extends BaseFragment implements View.OnClickListener {


    RelativeLayout rl_add_card;
    RecyclerView recyclerView2;

    EditText etCardName;
    EditText etEnterMoney;
    ImageView ivClose;
    ImageView ivOk;
    LinearLayout llCards;
    @InjectView(R.id.tv_total)
    TextView tvTotal;

    private List<Account> accounts;
    AccountDao dao;
    MyAdapter adapter;
    PopupWindow popupWindow;
    Button btn_add_card;
    SwipeRefreshLayout swipeRefreshLayout2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDatas();
        initViews();
    }

    private void initDatas() {
        dao = new AccountDao(getContext());
        accounts = new ArrayList<>();
        accounts = dao.selectAll();
        float totalSum = dao.getTotalSum();
        tvTotal.setText(totalSum + "");
    }

    private void initViews() {
        initPopupWindow();
        btn_add_card = (Button) getActivity().findViewById(R.id.btn_add_card);
        btn_add_card.setOnClickListener(this);
        recyclerView2 = (RecyclerView) getActivity().findViewById(R.id.rv2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyAdapter();
        recyclerView2.setAdapter(adapter);
        swipeRefreshLayout2 = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipeRefreshLayout2);
        swipeRefreshLayout2.setColorSchemeResources(R.color.common_green, R.color.text_color_red);
        swipeRefreshLayout2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshDatas();
                swipeRefreshLayout2.setRefreshing(false);
            }
        });
    }

    private void refreshDatas() {
        accounts = dao.selectAll();
        recyclerView2.setAdapter(adapter);
        float totalSum = dao.getTotalSum();
        tvTotal.setText(totalSum + "");
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final Account account = accounts.get(position);
            //设置背景色
            int id = R.drawable.bg_card1;
            switch ((position + 6) % 6) {
                case 0:
                    id = R.drawable.bg_card1;
                    break;
                case 1:
                    id = R.drawable.bg_card2;
                    break;
                case 2:
                    id = R.drawable.bg_card3;
                    break;
                case 3:
                    id = R.drawable.bg_card4;
                    break;
                case 4:
                    id = R.drawable.bg_card5;
                    break;
                case 5:
                    id = R.drawable.bg_card6;
                    break;
            }
            holder.rl_layout.setBackgroundResource(id);
            holder.tv_sum.setText(account.getSum() + "");
            holder.view.setClickable(true);
            holder.tv_account_name.setText(account.getAccountName());
            holder.view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.i("xsx", "onLongClick");
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
                            dao.delete(account.getId());
                            refreshDatas();
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
            return accounts.size();
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);

        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ImageView iv_icon;
        public TextView tv_account_name;
        public TextView tv_sum;
        public RelativeLayout rl_layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
            tv_account_name = (TextView) view.findViewById(R.id.tv_account_name);
            tv_sum = (TextView) view.findViewById(R.id.tv_sum);
            rl_layout = (RelativeLayout) view.findViewById(R.id.rl_layout);
        }
    }

    /**
     * flag，判断是添加还更新
     */
    boolean isAdd = true;

    private void initPopupWindow() {
        popupWindow = new PopupWindow(500, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.popup_add_card, null);
        etCardName = (EditText) contentView.findViewById(R.id.et_card_name);
        etEnterMoney = (EditText) contentView.findViewById(R.id.et_enter_money);
        ivClose = (ImageView) contentView.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(this);
        ivOk = (ImageView) contentView.findViewById(R.id.iv_ok);
        ivOk.setOnClickListener(this);
        popupWindow.setContentView(contentView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_card:
                showAddPopupWindow(view);
                break;
            case R.id.iv_close:
                popupWindow.dismiss();
                break;
            case R.id.iv_ok:
                addCard();
                break;
        }
    }

    private void addCard() {
        String accountName = etCardName.getText().toString();
        if (TextUtils.isEmpty(accountName)) {
            showToast("卡名不能为空");
            return;
        }
        if (TextUtils.isEmpty(etEnterMoney.getText().toString())) {
            showToast("余额不能为空");
            return;
        }
        float sum = Float.parseFloat(etEnterMoney.getText().toString());
        Account account = new Account(accountName, sum);
        if (dao.insert(account)) {
            showToast("添加卡成功");
            popupWindow.dismiss();
            refreshDatas();
        }

    }

    private void showAddPopupWindow(View view) {
        etCardName.setText("");
        etEnterMoney.setText("");
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }


}
