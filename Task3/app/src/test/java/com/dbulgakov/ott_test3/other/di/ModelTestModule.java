package com.dbulgakov.ott_test3.other.di;

import com.dbulgakov.ott_test3.model.api.ApiInterface;
import com.dbulgakov.ott_test3.other.Const;
import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.mock;

@Module
public class ModelTestModule {

    @Provides
    @Singleton
    ApiInterface provideApiInterface() {
        return mock(ApiInterface.class);
    }

    @Provides
    @Singleton
    @Named(Const.UI_THREAD)
    Scheduler provideSchedulerUI() {
        return Schedulers.immediate();
    }

    @Provides
    @Singleton
    @Named(Const.IO_THREAD)
    Scheduler provideSchedulerIO() {
        return Schedulers.immediate();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }
}
