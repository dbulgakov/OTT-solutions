package com.dbulgakov.task2.model.api;

import com.dbulgakov.task2.model.pojo.UserOrder;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface ApiInterface {

    @GET("orders/{userId}.json")
    Observable<List<UserOrder>> getUserOrders(@Path("userId") int userId);
}
