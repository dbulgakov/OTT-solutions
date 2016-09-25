package com.dbulgakov.task2.model.api;

import com.dbulgakov.task2.model.pojo.UserOrder;
import com.dbulgakov.task2.other.BaseTest;

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

public class ApiInterfaceTest extends BaseTest {
    private MockWebServer server;
    private ApiInterface apiInterface;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        server = new MockWebServer();
        server.start();

        final Dispatcher dispatcher = new Dispatcher() {

            @Override
            public MockResponse dispatch(RecordedRequest request) throws InterruptedException {

                if (request.getPath().equals("/orders.json" )) {
                    return new MockResponse().setResponseCode(200)
                            .setBody(testUtils.readString("json/orders"));
                }
                return new MockResponse().setResponseCode(404);
            }
        };

        server.setDispatcher(dispatcher);
        HttpUrl baseUrl = server.url("/");
        apiInterface = ApiModule.getApiInterface(baseUrl.toString());
    }

    @Test
    public void testGetRepositories() throws Exception {

        TestSubscriber<List<UserOrder>> testSubscriber = new TestSubscriber<>();
        apiInterface.getUserOrders().subscribe(testSubscriber);

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

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }
}
