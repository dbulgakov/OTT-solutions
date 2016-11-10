package com.dbulgakov.ott_test3.model.api;

import com.dbulgakov.ott_test3.model.DTO.HotelInfo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface ApiInterface {

    @GET("hotels/{cityId}.json")
    Observable<List<HotelInfo>> getHotels(@Path("cityId") int cityId);
}
