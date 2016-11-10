package com.dbulgakov.task2.other.di;

import com.dbulgakov.task2.model.ModelImpl;
import com.dbulgakov.task2.presenter.BasePresenter;
import com.dbulgakov.task2.presenter.OrdersPresenter;
import com.dbulgakov.task2.view.fragments.OrdersFragment;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {ModelModule.class, PresenterModule.class, ViewModule.class})
public interface AppComponent {

    void inject(ModelImpl dataRepository);

    void inject(BasePresenter basePresenter);

    void inject(OrdersPresenter ordersPresenter);

    void inject(OrdersFragment ordersFragment);
}
