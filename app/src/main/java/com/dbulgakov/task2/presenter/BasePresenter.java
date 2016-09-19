package com.dbulgakov.task2.presenter;

import com.dbulgakov.task2.model.Model;
import com.dbulgakov.task2.other.App;
import javax.inject.Inject;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public class BasePresenter implements Presenter{
    @Inject
    protected Model model;

    @Inject
    protected CompositeSubscription compositeSubscription;

    public BasePresenter() {
        App.getComponent().inject(this);
    }

    protected void addSubscription(Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    @Override
    public void onStop() {
        compositeSubscription.clear();
    }
}
