package com.dbulgakov.task2.other.di;

import com.dbulgakov.task2.model.pojo.UserOrder;
import com.dbulgakov.task2.other.TestUtils;

import java.util.Arrays;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataTestModule {
    private TestUtils testUtils;

    public DataTestModule() {
        testUtils = new TestUtils();
    }

    @Provides
    @Singleton
    List<UserOrder> provideUserOrderList() {
        UserOrder[] userOrderArray = testUtils.getGson().fromJson(testUtils.readString("json/orders"), UserOrder[].class);
        return Arrays.asList(userOrderArray);
    }
}
