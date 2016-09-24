package com.dbulgakov.task2.model.api;

import com.dbulgakov.task2.model.api.ApiInterface;
import com.dbulgakov.task2.other.App;

import java.io.File;
import java.util.UUID;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiModule {
    private ApiModule() {

    }

    public static ApiInterface getApiInterface(String url) {

        File cacheDir = new File(App.getContext().getCacheDir(), UUID.randomUUID().toString());
        Cache cache = new Cache(cacheDir, 1024);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(cache).build();


        Retrofit.Builder builder = new Retrofit.Builder().
                baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        return builder.build().create(ApiInterface.class);
    }
}
