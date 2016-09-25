package com.dbulgakov.task2.other.di;

import com.dbulgakov.task2.model.Model;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

import static org.mockito.Mockito.mock;

@Module
public class PresenterTestModule {

    @Provides
    @Singleton
    Model provideDataRepository() {
        return mock(Model.class);
    }

    @Provides
    CompositeSubscription provideCompositeSubscription() {
        return new CompositeSubscription();
    }
}
