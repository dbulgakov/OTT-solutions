package com.dbulgakov.task2.other.di;

import com.dbulgakov.task2.model.ModelImplTest;
import com.dbulgakov.task2.presenter.BasePresenter;
import com.dbulgakov.task2.presenter.OrdersPresenterTest;
import com.dbulgakov.task2.view.fragments.OrdersFragmentTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ModelTestModule.class, PresenterTestModule.class, ViewModuleTest.class, DataTestModule.class})
public interface TestComponent extends AppComponent {
    void inject(ModelImplTest modelImplTest);

    void inject(OrdersPresenterTest ordersPresenterTest);

    void inject(BasePresenter basePresenter);

    void inject(OrdersFragmentTest ordersFragmentTest);
}
