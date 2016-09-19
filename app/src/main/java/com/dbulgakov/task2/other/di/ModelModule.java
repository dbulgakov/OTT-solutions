package com.dbulgakov.task2.other.di;

import com.dbulgakov.task2.model.api.ApiInterface;
import com.dbulgakov.task2.model.api.ApiModule;
import com.dbulgakov.task2.other.Const;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class ModelModule {

    @Provides
    @Singleton
    ApiInterface provideApiInterface() {
        return ApiModule.getApiInterface(Const.BASE_URL);
    }
}
