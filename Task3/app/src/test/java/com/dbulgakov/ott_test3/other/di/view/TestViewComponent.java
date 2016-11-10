package com.dbulgakov.ott_test3.other.di.view;

import com.dbulgakov.ott_test3.view.fragments.MainFragmentTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestViewDynamicModule.class})
public interface TestViewComponent extends ViewComponent{
    void inject(MainFragmentTest mainFragmentTest);
}
