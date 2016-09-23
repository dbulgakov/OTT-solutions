package com.dbulgakov.task2.presenter;

import android.util.Log;

import com.dbulgakov.task2.model.pojo.UserOrder;
import com.dbulgakov.task2.other.App;
import com.dbulgakov.task2.view.fragments.ActiveOrdersView;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;

public class ActiveOrdersPresenter extends BasePresenter{

    private ActiveOrdersView activeOrdersView;

    @Inject
    public ActiveOrdersPresenter() {
    }

    public ActiveOrdersPresenter(ActiveOrdersView activeOrdersView) {
        super();
        App.getComponent().inject(this);
        this.activeOrdersView = activeOrdersView;
    }

    public void getActiveOrders(){
        Subscription subscription = model.getUserOrders()
                .subscribe(new Observer<List<UserOrder>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        activeOrdersView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<UserOrder> list) {
                        if (list != null && !list.isEmpty()) {
                            activeOrdersView.showActiveOrders(list);
                        } else {
                            //activeOrdersView.showEmptyList();
                        }
                    }
                });
        addSubscription(subscription);
    }
}
