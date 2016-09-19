package com.dbulgakov.task2.presenter;

import android.view.View;
import com.dbulgakov.task2.other.App;
import javax.inject.Inject;

public class ActiveOrdersPresenter extends BasePresenter{

    private View ordersView;

    @Inject
    public ActiveOrdersPresenter() {
    }

    public ActiveOrdersPresenter(View ordersView) {
        super();
        App.getComponent().inject(this);
        this.ordersView = ordersView;
    }
}
