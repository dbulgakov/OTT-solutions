package com.dbulgakov.other;

import com.dbulgakov.task2.other.App;
import com.dbulgakov.task2.other.di.AppComponent;

public class TestApplication extends App {

    @Override
    protected AppComponent buildComponent() {
        return DaggerTestComponent.builder()
                .build();
    }
}
