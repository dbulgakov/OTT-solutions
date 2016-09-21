package com.dbulgakov.task2.view.fragments;

import android.os.Bundle;
import android.view.*;

import com.dbulgakov.task2.R;
import com.dbulgakov.task2.model.pojo.UserOrder;
import com.dbulgakov.task2.presenter.ActiveOrdersPresenter;
import com.dbulgakov.task2.presenter.Presenter;

import java.util.List;

import javax.inject.Inject;

public class ActiveOrdersFragment extends BaseFragment implements ActiveOrdersView{


    @Inject
    ActiveOrdersPresenter presenter;

    @Override
    public void showActiveOrders(List<UserOrder> orderList) {

    }

    @Override
    protected Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void showError(String error) {
        makeToast(error);
    }

    private void makeToast(String text) {
        //Snackbar.make(getView(), text, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order, container, false);
    }
}
