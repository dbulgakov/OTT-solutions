package com.dbulgakov.task2.other.di.view;

import com.dbulgakov.task2.presenter.OrdersPresenter;
import com.dbulgakov.task2.view.fragments.OrdersView;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewDynamicModule {
    private final OrdersView view;

    public ViewDynamicModule(OrdersView view) {
        this.view = view;
    }

    @Provides
    OrdersPresenter provideRepoListPresenter() {
        return new OrdersPresenter(view);
    }
}
