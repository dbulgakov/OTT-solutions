package com.dbulgakov.task2.view.fragments;

import com.dbulgakov.task2.model.pojo.UserOrderDTO;

import java.util.List;

public interface ActiveOrdersView extends View{
    void showActiveOrders(List<UserOrderDTO> orderList);
}
