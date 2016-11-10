package com.dbulgakov.ott_test3.other.di;

import com.dbulgakov.ott_test3.model.Model;
import com.dbulgakov.ott_test3.model.ModelImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

@Module
public class PresenterModule {

    @Provides
    @Singleton
    Model provideDataRepository() {
        return new ModelImpl();
    }

    @Provides
    CompositeSubscription provideCompositeSubscription() {
        return new CompositeSubscription();
    }
}
