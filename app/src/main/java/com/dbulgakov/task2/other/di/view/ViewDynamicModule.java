package com.dbulgakov.task2.other.di.view;

import com.dbulgakov.task2.presenter.ActiveOrdersPresenter;
import com.dbulgakov.task2.view.fragments.ActiveOrdersView;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewDynamicModule {
    private ActiveOrdersView view;

    public ViewDynamicModule(ActiveOrdersView view) {
        this.view = view;
    }

    @Provides
    ActiveOrdersPresenter provideRepoListPresenter() {
        return new ActiveOrdersPresenter(view);
    }
}
