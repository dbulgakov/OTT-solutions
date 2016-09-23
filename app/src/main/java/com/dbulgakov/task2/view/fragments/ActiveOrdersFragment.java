package com.dbulgakov.task2.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.view.View;
import android.widget.TextView;

import com.dbulgakov.task2.R;
import com.dbulgakov.task2.model.pojo.UserOrder;
import com.dbulgakov.task2.other.App;
import com.dbulgakov.task2.other.di.DaggerAppComponent;
import com.dbulgakov.task2.other.di.view.DaggerViewComponent;
import com.dbulgakov.task2.other.di.view.ViewComponent;
import com.dbulgakov.task2.other.di.view.ViewDynamicModule;
import com.dbulgakov.task2.presenter.ActiveOrdersPresenter;
import com.dbulgakov.task2.presenter.Presenter;
import com.dbulgakov.task2.view.adapters.OrderListAdapter;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActiveOrdersFragment extends BaseFragment implements ActiveOrdersView{

    @BindView(R.id.orders_recycler_view)
    RecyclerView ordersRecyclerView;

    @BindView(R.id.no_data_text_view)
    TextView noDataTextView;

    @Inject
    ActiveOrdersPresenter presenter;

    private OrderListAdapter orderListAdapter;
    private ViewComponent viewComponent;

    @Override
    public void addOrderToList(UserOrder userOrder) {
        orderListAdapter.addUserOrder(userOrder);
    }

    @Override
    protected Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void showError(Throwable throwable) {
        if (throwable instanceof UnknownHostException) {
            makeToast(getString(R.string.error_no_internet_message));
        } else
            makeToast(getString(R.string.error_unknown_error_message));
    }

    private void makeToast(String text) {
        Snackbar.make(ordersRecyclerView, text, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (viewComponent == null) {
            viewComponent = DaggerViewComponent.builder()
                    .viewDynamicModule(new ViewDynamicModule(this))
                    .build();
        }
        viewComponent.inject(this);
        presenter.getActiveOrders();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        ButterKnife.bind(this, view);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        ordersRecyclerView.setLayoutManager(llm);
        orderListAdapter = new OrderListAdapter(new ArrayList<>(), presenter);
        ordersRecyclerView.setAdapter(orderListAdapter);
        return view;
    }
}
