package com.dbulgakov.task2.model.api;

import com.dbulgakov.task2.model.pojo.UserOrderDTO;
import java.util.List;
import retrofit2.http.GET;
import rx.Observable;

public interface ApiInterface {

    @GET("orders.json")
    Observable<List<UserOrderDTO>> getUserOrders();
}
