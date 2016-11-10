package com.dbulgakov.ott_test3.presenter;

import com.dbulgakov.ott_test3.other.App;
import com.dbulgakov.ott_test3.other.Const;
import com.dbulgakov.ott_test3.view.fragments.MainView;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Scheduler;
import rx.Subscription;

public class MainPresenter extends BasePresenter{

    private MainView view;
    private final static int CITY_ID = 1; // 1 - 5

    @Inject
    @Named(Const.UI_THREAD)
    Scheduler uiThread;

    @Inject
    @Named(Const.IO_THREAD)
    Scheduler ioThread;

    @Inject
    public MainPresenter() {
    }

    public MainPresenter(MainView view) {
        super();
        App.getComponent().inject(this);
        this.view = view;
    }

    public void onGetHotelPricesRangesButtonClick() {
        view.startProgressBar();
        Subscription subscription = model.getCommonPriceRanges(CITY_ID)
                .subscribeOn(ioThread)
                .observeOn(uiThread)
                .subscribe(hotelRanges -> {
                    view.startPriceRangesDialog(hotelRanges);
                }, throwable -> {
                    view.showError(throwable);
                    throwable.printStackTrace();
                });
        addSubscription(subscription);
        view.stopProgressBar();
    }
}
