package com.dbulgakov.task2.other.di;

import com.dbulgakov.task2.model.ModelImpl;
import com.dbulgakov.task2.presenter.ActiveOrdersPresenter;
import com.dbulgakov.task2.presenter.BasePresenter;
import com.dbulgakov.task2.view.fragments.ActiveOrdersFragment;

import javax.inject.Singleton;
import dagger.Component;


@Singleton
@Component(modules = {ModelModule.class, PresenterModule.class, ViewModule.class})
public interface AppComponent {

    void inject(ModelImpl dataRepository);

    void inject(BasePresenter basePresenter);

    void inject(ActiveOrdersPresenter activeOrdersPresenter);

    void inject(ActiveOrdersFragment activeOrdersFragment);
}
