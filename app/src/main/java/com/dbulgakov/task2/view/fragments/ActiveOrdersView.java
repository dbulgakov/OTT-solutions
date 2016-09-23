package com.dbulgakov.task2.view.fragments;

import com.dbulgakov.task2.model.pojo.UserOrder;

import java.util.List;

public interface ActiveOrdersView extends View{
    void setOrderList(List<UserOrder> userOrderList);
    void addOrderToList(UserOrder userOrder);
    List<UserOrder> getCurrentUserOrderList();
}
