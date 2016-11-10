package com.dbulgakov.ott_test3.model.api;

import com.dbulgakov.ott_test3.model.DTO.HotelInfo;
import com.dbulgakov.ott_test3.other.BaseTest;
import com.dbulgakov.ott_test3.other.TestConst;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ApiInterfaceTest extends BaseTest{
    private ApiInterface apiInterface;
    private MockWebServer server;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        server = new MockWebServer();
        server.start();

        final Dispatcher dispatcher = new Dispatcher() {

            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {

                if (request.getPath().equals("/hotels/1.json")) {
                    return new MockResponse().setResponseCode(200)
                            .setBody(testUtils.readString("json/jsonLotsHotels"));
                }
                if (request.getPath().equals("/hotels/5.json")) {
                    return new MockResponse().setResponseCode(200)
                            .setBody(testUtils.readString("json/jsonNoHotels"));
                }
                return new MockResponse().setResponseCode(404);
            }
        };

        server.setDispatcher(dispatcher);
        HttpUrl baseUrl = server.url("/");
        apiInterface = ApiModule.getApiInterface(baseUrl.toString());
    }

    @Test
    public void testGetHotels() throws Exception {

        TestSubscriber<List<HotelInfo>> testSubscriber = new TestSubscriber<>();
        apiInterface.getHotels(TestConst.TEST_CITY_ID_WITH_LOTS_OF_HOTELS).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<HotelInfo> actual = testSubscriber.getOnNextEvents().get(0);

        assertEquals(1500, actual.size());

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
    public void testGetHotelsNoHotels() throws Exception {

        TestSubscriber<List<HotelInfo>> testSubscriber = new TestSubscriber<>();
        apiInterface.getHotels(TestConst.TEST_CITY_ID_WITH_NO_HOTELS).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<HotelInfo> actual = testSubscriber.getOnNextEvents().get(0);

        assertEquals(0, actual.size());
    }

    @Test
    public void testGetWrongUserId() throws Exception {
        try {
            apiInterface.getHotels(TestConst.TEST_CITY_ID_WRONG_ID).subscribe();
            fail();
        } catch (Exception expected) {
            assertEquals("HTTP 404 Client Error", expected.getMessage());
        }
    }


    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }
}
