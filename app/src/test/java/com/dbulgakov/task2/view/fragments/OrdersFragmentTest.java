package com.dbulgakov.task2.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dbulgakov.task2.R;
import com.dbulgakov.task2.other.BaseTest;
import com.dbulgakov.task2.other.Const;
import com.dbulgakov.task2.other.di.view.DaggerTestViewComponent;
import com.dbulgakov.task2.other.di.view.TestViewComponent;
import com.dbulgakov.task2.other.di.view.TestViewDynamicModule;
import com.dbulgakov.task2.presenter.OrdersPresenter;
import com.dbulgakov.task2.view.activities.MainActivity;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.robolectric.Robolectric;

import javax.inject.Inject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class OrdersFragmentTest extends BaseTest{

    @Inject
    OrdersPresenter ordersPresenter;

    private OrdersFragment ordersFragment;

    private MainActivity activity;

    private Bundle bundle;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        TestViewComponent testViewComponent = DaggerTestViewComponent.builder()
                .testViewDynamicModule(new TestViewDynamicModule())
                .build();


        testViewComponent.inject(this);
        ordersFragment = new OrdersFragment();
        activity = Robolectric.setupActivity(MainActivity.class);

        ordersFragment.setViewComponent(testViewComponent);
        ordersFragment.onCreate(getArgsBundle(Const.FRAGMENT_ACTIVE));
    }

    @Ignore
    @Test
    public void testOnStop() {
        ordersFragment.onStop();
        verify(ordersPresenter).onStop();
    }


    private Bundle getArgsBundle(int value) {
        Bundle args = new Bundle();
        args.putInt(Const.FRAGMENT_KEY, value);
        return args;
    }
}
