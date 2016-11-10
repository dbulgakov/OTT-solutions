package com.dbulgakov.ott_test3.other.di;

import com.dbulgakov.ott_test3.model.ModelImplTest;
import com.dbulgakov.ott_test3.presenter.BasePresenter;
import com.dbulgakov.ott_test3.presenter.HotelPricesRangesDialogPresenterTest;
import com.dbulgakov.ott_test3.presenter.MainPresenterTest;
import com.dbulgakov.ott_test3.view.fragments.HotelPricesRangesDialogFragmentTest;

import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {ModelTestModule.class, PresenterModuleTest.class, ViewModuleTest.class, DataTestModule.class})
public interface TestComponent extends AppComponent {
    void inject(ModelImplTest modelImplTest);

    void inject(BasePresenter basePresenter);

    void inject(MainPresenterTest mainPresenterTest);

    void inject(HotelPricesRangesDialogFragmentTest hotelPricesRangesDialogFragmentTest);

    void inject(HotelPricesRangesDialogPresenterTest hotelPricesRangesDialogPresenterTest);
}
