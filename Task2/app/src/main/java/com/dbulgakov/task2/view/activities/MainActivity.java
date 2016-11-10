package com.dbulgakov.task2.view.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.dbulgakov.task2.R;
import com.dbulgakov.task2.other.Const;
import com.dbulgakov.task2.view.adapters.ViewPagerAdapter;
import com.dbulgakov.task2.view.fragments.OrdersFragment;

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
        viewPagerAdapter.addFragment(initializeOrdersFragment(Const.FRAGMENT_ACTIVE), getString(R.string.active_orders_tab_name_string));
        viewPagerAdapter.addFragment(initializeOrdersFragment(Const.FRAGMENT_ARCHIVE), getString(R.string.archive_orders_tab_name_string));
        fragmentViewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(fragmentViewPager);
    }

    private OrdersFragment initializeOrdersFragment(int fragmentType) {
        OrdersFragment ordersFragment = new OrdersFragment();
        // Можно делать OrderFragment.getInstance(fragmentType), чтобы фрагмент уже сам делал setData.
        // Так мы изолируем всю работу с Bundle фрагмента внутри фрагмента и не нужно будет выносить
        // какие-то отдельные константы.
        Bundle args = new Bundle();
        args.putInt(Const.FRAGMENT_KEY, fragmentType);
        ordersFragment.setArguments(args);
        return ordersFragment;
    }
}
