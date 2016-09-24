package com.dbulgakov.task2.presenter;

import android.os.Bundle;
import android.util.Log;

import com.dbulgakov.task2.model.pojo.UserOrder;
import com.dbulgakov.task2.other.App;
import com.dbulgakov.task2.view.fragments.ActiveOrdersView;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

public class ActiveOrdersPresenter extends BasePresenter{

    private ActiveOrdersView activeOrdersView;
    private static final String SAVED_USER_ORDERS_KEY = "SAVED_ORDERS_KEY";

    @Inject
    public ActiveOrdersPresenter() {
    }

    public ActiveOrdersPresenter(ActiveOrdersView activeOrdersView) {
        super();
        App.getComponent().inject(this);
        this.activeOrdersView = activeOrdersView;
    }

    private void getActiveOrdersFromBackend(){
        Date today = new Date();
        Subscription subscription = model.getUserOrders()
                .flatMap(Observable::from)
                .filter(order -> order.getArrivalAt().after(today) && !order.getUserCancel())
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

    public void onSaveInstanceState(Bundle outState){
        List<UserOrder> userOrderList = activeOrdersView.getCurrentUserOrderList();
        if (userOrderList != null && !userOrderList.isEmpty()) {
            outState.putSerializable(SAVED_USER_ORDERS_KEY, new ArrayList<>(userOrderList));
        }
    }

    public void getActiveOrders(Bundle savedInstanceState) {
        activeOrdersView.startSwipeRefreshing();
        if (savedInstanceState != null) {
            List<UserOrder> userOrderList= (List<UserOrder>) savedInstanceState.getSerializable(SAVED_USER_ORDERS_KEY);
            activeOrdersView.setOrderList(userOrderList);
        } else {
            activeOrdersView.clearOrderList();
            getActiveOrdersFromBackend();
        }
    }

    public void getActiveOrders() {
        getActiveOrders(null);
    }
}
