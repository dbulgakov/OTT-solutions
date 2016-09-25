package com.dbulgakov.task2.other.di;

import com.dbulgakov.task2.other.TestUtils;

import dagger.Module;

@Module
public class DataTestModule {
    private TestUtils testUtils;

    public DataTestModule() {
        testUtils = new TestUtils();
    }
}
