package com.dbulgakov.ott_test3.other.di;

import com.dbulgakov.ott_test3.presenter.HotelPricesRangesDialogPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public class ViewModuleTest {

    @Provides
    @Singleton
    HotelPricesRangesDialogPresenter provideRepoInfoPresenter() {
        return mock(HotelPricesRangesDialogPresenter.class);
    }
}