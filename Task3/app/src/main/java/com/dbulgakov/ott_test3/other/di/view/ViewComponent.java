package com.dbulgakov.ott_test3.other.di.view;

import com.dbulgakov.ott_test3.view.fragments.MainFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ViewDynamicModule.class})
public interface ViewComponent {
    void inject(MainFragment mainFragment);
}
