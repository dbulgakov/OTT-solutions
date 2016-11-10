package com.dbulgakov.task2.other;

import android.app.Application;
import android.content.Context;

import com.dbulgakov.task2.other.di.AppComponent;
import com.dbulgakov.task2.other.di.DaggerAppComponent;


public class App extends Application {
    private static AppComponent component;
    private static App appInstance;

    public static AppComponent getComponent() {
        return component;
    }

    public static Context getContext() {
        return appInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        component = buildComponent();
    }

    protected AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .build();
    }
}
