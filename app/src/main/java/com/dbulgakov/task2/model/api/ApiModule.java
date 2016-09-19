package com.dbulgakov.task2.model.api;

import com.dbulgakov.task2.model.api.ApiInterface;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiModule {
    private ApiModule() {

    }

    public static ApiInterface getApiInterface(String url) {

        Retrofit.Builder builder = new Retrofit.Builder().
                baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        builder.client(new OkHttpClient());

        return builder.build().create(ApiInterface.class);
    }
}
