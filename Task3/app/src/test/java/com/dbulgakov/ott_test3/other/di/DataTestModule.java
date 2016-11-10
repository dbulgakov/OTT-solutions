package com.dbulgakov.ott_test3.other.di;

import com.dbulgakov.ott_test3.model.DTO.HotelInfo;
import com.dbulgakov.ott_test3.other.TestUtils;

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
    List<HotelInfo> provideHotelInfoList() {
        HotelInfo[] hotelInfos = testUtils.getGson().fromJson(testUtils.readString("json/jsonLotsHotels"), HotelInfo[].class);
        return Arrays.asList(hotelInfos);
    }
}
