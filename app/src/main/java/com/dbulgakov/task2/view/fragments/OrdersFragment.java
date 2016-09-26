package com.dbulgakov.task2.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dbulgakov.task2.R;
import com.dbulgakov.task2.model.pojo.UserOrder;
import com.dbulgakov.task2.other.di.view.DaggerViewComponent;
import com.dbulgakov.task2.other.di.view.ViewComponent;
import com.dbulgakov.task2.other.di.view.ViewDynamicModule;
import com.dbulgakov.task2.presenter.OrdersPresenter;
import com.dbulgakov.task2.presenter.Presenter;
import com.dbulgakov.task2.view.adapters.OrderListAdapter;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrdersFragment extends BaseFragment implements OrdersView, SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.orders_recycler_view)
    RecyclerView ordersRecyclerView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    OrdersPresenter presenter;

    private OrderListAdapter orderListAdapter;
    private ViewComponent viewComponent;

    @Override
    public void setOrderList(List<UserOrder> userOrderList) {
        if (userOrderList != null) {
            orderListAdapter.setUserOrderList(userOrderList);
        }
        stopSwipeRefreshing();
    }

    @Override
    public void addOrderToList(UserOrder userOrder) {
        orderListAdapter.addUserOrder(userOrder);
        stopSwipeRefreshing();
    }

    @Override
    public void clearOrderList() {
        orderListAdapter.clearData();
    }

    @Override
    public void startSwipeRefreshing() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void stopSwipeRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public List<UserOrder> getCurrentUserOrderList() {
        return orderListAdapter.getUserOrderList();
    }


    @Override
    protected Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void showError(Throwable throwable) {
        stopSwipeRefreshing();
        if (throwable instanceof UnknownHostException) {
            makeToast(getString(R.string.error_no_internet_message));
        } else {
            makeToast(getString(R.string.error_unknown_error_message));
        }
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
        presenter.onCreate(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        ButterKnife.bind(this, view);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        ordersRecyclerView.setLayoutManager(llm);
        orderListAdapter = new OrderListAdapter(new ArrayList<>());
        ordersRecyclerView.setAdapter(orderListAdapter);

        presenter.getUserOrders(savedInstanceState);
        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveInstanceState(outState);
    }

    @Override
    public void onRefresh() {
        presenter.getUserOrders();
    }

    public void setViewComponent(ViewComponent viewComponent) {
        this.viewComponent = viewComponent;
    }
}
