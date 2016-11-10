package com.dbulgakov.ott_test3.other.di;

import com.dbulgakov.ott_test3.presenter.HotelPricesRangesDialogPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModule {

    @Provides
    HotelPricesRangesDialogPresenter provideHotelPricesRangesDialogPresenter() {
        return new HotelPricesRangesDialogPresenter();
    }
}
