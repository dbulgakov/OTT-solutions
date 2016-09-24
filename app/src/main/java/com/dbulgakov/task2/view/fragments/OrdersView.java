package com.dbulgakov.task2.view.fragments;

import com.dbulgakov.task2.model.pojo.UserOrder;

import java.util.List;

public interface OrdersView extends View{
    void setOrderList(List<UserOrder> userOrderList);
    void addOrderToList(UserOrder userOrder);
    void clearOrderList();
    void startSwipeRefreshing();
    void stopSwipeRefreshing();
    List<UserOrder> getCurrentUserOrderList();
}
