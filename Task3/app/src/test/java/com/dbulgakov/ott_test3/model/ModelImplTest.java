package com.dbulgakov.ott_test3.model;

import com.dbulgakov.itemranger.Range;
import com.dbulgakov.ott_test3.model.DTO.HotelInfo;
import com.dbulgakov.ott_test3.model.api.ApiInterface;
import com.dbulgakov.ott_test3.other.BaseTest;
import com.dbulgakov.ott_test3.other.TestConst;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ModelImplTest extends BaseTest{
    @Inject
    ApiInterface apiInterface;

    Model model;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);
        model = new ModelImpl();
    }

    @Test
    public void testGetAvailableHotels() {
        HotelInfo[] availableHotels = testUtils.getGson().fromJson(testUtils.readString("json/jsonLotsHotels"), HotelInfo[].class);

        when(apiInterface.getHotels(TestConst.TEST_CITY_ID_WITH_LOTS_OF_HOTELS)).thenReturn(Observable.just(Arrays.asList(availableHotels)));

        TestSubscriber<List<HotelInfo>> testSubscriber = new TestSubscriber<>();
        model.getAvailableHotels(TestConst.TEST_CITY_ID_WITH_LOTS_OF_HOTELS).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<HotelInfo> actual = testSubscriber.getOnNextEvents().get(0);

        assertEquals(1, actual.get(0).getHotelId());
        assertEquals("Simple hotel name #1", actual.get(0).getName());
        assertEquals(16194, actual.get(0).getPrice());
        assertEquals(16194, actual.get(0).getValueToRangeBy());

        assertEquals(2, actual.get(1).getHotelId());
        assertEquals("Simple hotel name #2", actual.get(1).getName());
        assertEquals(7468, actual.get(1).getPrice());
        assertEquals(7468, actual.get(1).getValueToRangeBy());
    }

    @Test
    public void getCommonPriceRanges() {
        HotelInfo[] availableHotels = testUtils.getGson().fromJson(testUtils.readString("json/jsonNoHotels"), HotelInfo[].class);

        when(apiInterface.getHotels(TestConst.TEST_CITY_ID_WITH_NO_HOTELS)).thenReturn(Observable.just(Arrays.asList(availableHotels)));

        TestSubscriber<List<Range>> testSubscriber = new TestSubscriber<>();
        model.getCommonPriceRanges(TestConst.TEST_CITY_ID_WITH_NO_HOTELS).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<Range> actual = testSubscriber.getOnNextEvents().get(0);

        assertEquals(1, actual.size());

        assertEquals(-1, actual.get(0).getStartValue());
    }
}
