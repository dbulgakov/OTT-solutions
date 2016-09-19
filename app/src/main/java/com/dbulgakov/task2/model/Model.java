package com.dbulgakov.task2.model;

import com.dbulgakov.task2.model.pojo.UserOrder;

import java.util.List;

import rx.Observable;

public interface Model {
    Observable<List<UserOrder>> getUserOrders();
}
