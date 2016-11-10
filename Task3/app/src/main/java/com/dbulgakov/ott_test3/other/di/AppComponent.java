package com.dbulgakov.ott_test3.other.di;

import com.dbulgakov.ott_test3.model.ModelImpl;
import com.dbulgakov.ott_test3.presenter.BasePresenter;
import com.dbulgakov.ott_test3.presenter.MainPresenter;
import com.dbulgakov.ott_test3.view.fragments.HotelPricesRangesDialogFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ModelModule.class, PresenterModule.class, ViewModule.class})
public interface AppComponent {

    void inject(ModelImpl dataRepository);

    void inject(BasePresenter basePresenter);

    void inject(MainPresenter mainPresenter);

    void inject(HotelPricesRangesDialogFragment hotelPricesRangesDialogFragment);
}