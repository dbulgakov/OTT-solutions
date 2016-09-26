package com.dbulgakov.task2.other.di.view;

import com.dbulgakov.task2.view.fragments.OrdersFragmentTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestViewDynamicModule.class})
public interface TestViewComponent extends ViewComponent{
    void inject(OrdersFragmentTest ordersFragment);
}