package com.xsx.account.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.xsx.account.Fragment.ExpenseFragment;
import com.xsx.account.Fragment.IncomeFragment;
import com.xsx.account.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AccountingActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tablayout)
    TabLayout tablayout;
    @InjectView(R.id.viewpager)
    ViewPager viewpager;

    List<Fragment> fragments;
    MyViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounting);
        ButterKnife.inject(this);
        initViews();
    }

    private void initViews() {
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountingActivity.this.finish();
            }
        });
        toolbar.setTitle("记一笔");

        fragments=new ArrayList<>();

        adapter=new MyViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ExpenseFragment(),"支出");
        adapter.addFragment(new IncomeFragment(),"收入");
        viewpager.setAdapter(adapter);

        tablayout.setupWithViewPager(viewpager);
        tablayout.setSelectedTabIndicatorHeight(6);
        tablayout.setSelectedTabIndicatorColor(Color.WHITE);
    }

    class MyViewPagerAdapter extends FragmentPagerAdapter{

        List<Fragment> fragments;
        List<String> titles;
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments=new ArrayList<>();
            titles=new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            titles.add(title);
        }
    }
}
