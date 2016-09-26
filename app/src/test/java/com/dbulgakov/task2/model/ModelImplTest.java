package com.dbulgakov.task2.model;

import com.dbulgakov.task2.model.api.ApiInterface;
import com.dbulgakov.task2.model.pojo.UserOrder;
import com.dbulgakov.task2.other.BaseTest;
import com.dbulgakov.task2.other.TestConst;

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
    public void testGetUserOrders() {
        UserOrder[] userOrders = testUtils.getGson().fromJson(testUtils.readString("json/jsonWithOrders"), UserOrder[].class);

        when(apiInterface.getUserOrders(TestConst.TEST_USER_ID_WITH_ORDERS)).thenReturn(Observable.just(Arrays.asList(userOrders)));

        TestSubscriber<List<UserOrder>> testSubscriber = new TestSubscriber<>();
        model.getUserOrders(TestConst.TEST_USER_ID_WITH_ORDERS).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<UserOrder> actual = testSubscriber.getOnNextEvents().get(0);

        assertEquals(8, actual.size());
        assertEquals("Vim airlines", actual.get(0).getAirline());
        assertEquals("DME", actual.get(0).getOrigin());
        assertEquals("LED", actual.get(0).getDestination());
        assertEquals("NN-775", actual.get(0).getFlightNumber());
        assertEquals(false, actual.get(0).getIfUserCancel());

        assertEquals(0, actual.get(3).getStopNumber());
        assertEquals("4:30", actual.get(3).getFlightDuration());
    }
}
