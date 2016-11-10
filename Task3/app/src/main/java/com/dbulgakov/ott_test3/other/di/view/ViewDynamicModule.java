package com.dbulgakov.ott_test3.other.di.view;

import com.dbulgakov.ott_test3.presenter.MainPresenter;
import com.dbulgakov.ott_test3.view.fragments.MainView;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewDynamicModule {
    private final MainView view;

    public ViewDynamicModule(MainView view) {
        this.view = view;
    }

    @Provides
    MainPresenter provideMainPresenter() {
        return new MainPresenter(view);
    }
}