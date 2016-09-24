package com.dbulgakov.task2.view.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.dbulgakov.task2.R;
import com.dbulgakov.task2.view.adapters.ViewPagerAdapter;
import com.dbulgakov.task2.view.fragments.ActiveOrdersFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fragment_view_pager)
    ViewPager fragmentViewPager;

    @BindView(R.id.tabs_view)
    TabLayout tabLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        initializeViewPager();
    }

    private void initializeViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new ActiveOrdersFragment(), getString(R.string.active_orders_tab_name_string));
        viewPagerAdapter.addFragment(new ActiveOrdersFragment(), getString(R.string.archive_orders_tab_name_string));
        fragmentViewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(fragmentViewPager);
    }
}
