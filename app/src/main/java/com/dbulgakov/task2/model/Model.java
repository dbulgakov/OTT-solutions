package com.dbulgakov.task2.model;

import com.dbulgakov.task2.model.pojo.UserOrderDTO;

import java.util.List;

import rx.Observable;

public interface Model {
    Observable<List<UserOrderDTO>> getUserOrders();
}
