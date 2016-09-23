package com.dbulgakov.task2.presenter;

import com.dbulgakov.task2.model.pojo.UserOrder;
import com.dbulgakov.task2.other.App;
import com.dbulgakov.task2.view.fragments.ActiveOrdersView;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
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
        Date today = new Date();
        Subscription subscription = model.getUserOrders()
                .flatMap(Observable::from)
                .filter(order -> order.getArrivalAt().after(today))
                .subscribe(new Observer<UserOrder>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        activeOrdersView.showError(e);
                    }

                    @Override
                    public void onNext(UserOrder userOrder) {
                        activeOrdersView.addOrderToList(userOrder);
                    }
                });
        addSubscription(subscription);
    }
}
