package com.dbulgakov.task2.view.fragments;

import com.dbulgakov.task2.model.pojo.UserOrderDTO;
import com.dbulgakov.task2.presenter.ActiveOrdersPresenter;
import com.dbulgakov.task2.presenter.Presenter;
import android.support.design.widget.Snackbar;

import java.util.List;

import javax.inject.Inject;

public class ActiveOrdersFragment extends BaseFragment implements ActiveOrdersView{


    @Inject
    ActiveOrdersPresenter presenter;

    @Override
    public void showActiveOrders(List<UserOrderDTO> orderList) {

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
}
