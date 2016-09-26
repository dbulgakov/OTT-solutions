package com.dbulgakov.task2.other.di.view;

import com.dbulgakov.task2.presenter.OrdersPresenter;
import com.dbulgakov.task2.view.fragments.OrdersFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public class TestViewDynamicModule{

    @Provides
    @Singleton
    OrdersPresenter provideOrdersPresenter() {
        return mock(OrdersPresenter.class);
    }
}
