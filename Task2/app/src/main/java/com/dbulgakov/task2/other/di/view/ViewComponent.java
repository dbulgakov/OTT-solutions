package com.dbulgakov.task2.other.di.view;

import com.dbulgakov.task2.view.fragments.OrdersFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ViewDynamicModule.class})
public interface ViewComponent {
    void inject(OrdersFragment activeOrdersFragment);
}
