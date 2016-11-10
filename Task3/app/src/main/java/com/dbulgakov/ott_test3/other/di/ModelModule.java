package com.dbulgakov.ott_test3.other.di;

import com.dbulgakov.ott_test3.model.api.ApiInterface;
import com.dbulgakov.ott_test3.model.api.ApiModule;
import com.dbulgakov.ott_test3.other.Const;
import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class ModelModule {

    @Provides
    @Singleton
    ApiInterface provideApiInterface() {
        return ApiModule.getApiInterface(Const.BASE_URL);
    }

    @Provides
    @Singleton
    @Named(Const.UI_THREAD)
    Scheduler provideSchedulerUI() {
        return AndroidSchedulers.mainThread();
    }


    @Provides
    @Singleton
    @Named(Const.IO_THREAD)
    Scheduler provideSchedulerIO() {
        return Schedulers.io();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }
}