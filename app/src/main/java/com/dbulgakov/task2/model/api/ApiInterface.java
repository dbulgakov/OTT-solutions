package com.dbulgakov.task2.model.api;

import com.dbulgakov.task2.model.pojo.UserOrder;
import java.util.List;
import retrofit2.http.GET;
import rx.Observable;

public interface ApiInterface {

    @GET("orders.json")
    Observable<List<UserOrder>> getUserOrders();
}
