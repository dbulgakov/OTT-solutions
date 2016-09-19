package com.dbulgakov.task2.other.di;

import com.dbulgakov.task2.model.api.ModelImpl;
import com.dbulgakov.task2.presenter.BasePresenter;
import javax.inject.Singleton;
import dagger.Component;


@Singleton
@Component(modules = {ModelModule.class, PresenterModule.class, ViewModule.class})
public interface AppComponent {

    void inject(ModelImpl dataRepository);

    void inject(BasePresenter basePresenter);

}
