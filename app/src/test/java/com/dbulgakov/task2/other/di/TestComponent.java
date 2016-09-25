package com.dbulgakov.task2.other.di;

import com.dbulgakov.task2.presenter.OrdersTestPresenter;
import com.dbulgakov.task2.view.fragments.OrdersTestFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ModelTestModule.class, PresenterTestModule.class, ViewTestModule.class, DataTestModule.class})
public interface TestComponent extends AppComponent {
    void inject(ModelTestModule modelTestModule);

    void inject(OrdersTestPresenter ordersTestPresenter);

    void inject(OrdersTestFragment ordersTestFragment);
}
