package com.dbulgakov.ott_test3.other;

import com.dbulgakov.ott_test3.other.di.AppComponent;
import com.dbulgakov.ott_test3.other.di.DaggerTestComponent;

public class TestApplication extends App {
    @Override
    protected AppComponent buildComponent() {
        return DaggerTestComponent.builder()
                .build();
    }
}
