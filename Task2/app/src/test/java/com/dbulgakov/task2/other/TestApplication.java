package com.dbulgakov.task2.other;

import com.dbulgakov.task2.other.di.AppComponent;
import com.dbulgakov.task2.other.di.DaggerTestComponent;

public class TestApplication extends App {

    @Override
    protected AppComponent buildComponent() {
        return DaggerTestComponent.builder()
                .build();
    }
}
