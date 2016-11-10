package com.dbulgakov.ott_test3.other.di.view;

import com.dbulgakov.ott_test3.presenter.MainPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public class TestViewDynamicModule{

    @Provides
    @Singleton
    MainPresenter provideMainPresenter() {
        return mock(MainPresenter.class);
    }
}
