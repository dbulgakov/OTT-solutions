package com.dbulgakov.task2.presenter;

import android.os.Bundle;

import com.dbulgakov.task2.model.Model;
import com.dbulgakov.task2.model.pojo.UserOrder;
import com.dbulgakov.task2.other.BaseTest;
import com.dbulgakov.task2.other.Const;
import com.dbulgakov.task2.other.TestConst;
import com.dbulgakov.task2.predicate.ActiveOrderPredicate;
import com.dbulgakov.task2.predicate.OtherOrdersPredicate;
import com.dbulgakov.task2.view.fragments.OrdersView;
import com.google.common.base.Predicate;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class OrdersTestPresenter extends BaseTest{
    @Inject
    protected Model model;

    @Inject
    protected List<UserOrder> orderList;


    private OrdersPresenter ordersPresenter;
    private OrdersView mockView;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);

        mockView = mock(OrdersView.class);
        ordersPresenter = new OrdersPresenter(mockView);

        doAnswer(invocation -> Observable.just(orderList))
                .when(model)
                .getUserOrders(TestConst.TEST_USER_ID_WITH_ORDERS);
    }

    @Test
    public void testGetActiveUserOrders() {
        ordersPresenter.onCreate(getArgs(Const.FRAGMENT_ACTIVE));
        ordersPresenter.getUserOrders();
        ordersPresenter.onStop();

        verify(mockView).setOrderList(filterOrders(orderList, new ActiveOrderPredicate()));
    }

    @Test
    public void testGetOtherUserOrders() {
        ordersPresenter.onCreate(getArgs(Const.FRAGMENT_ARCHIVE));
        ordersPresenter.getUserOrders();
        ordersPresenter.onStop();

        verify(mockView).setOrderList(filterOrders(orderList, new OtherOrdersPredicate()));
    }

    @Test
    public void testGetUserOrdersFromBundle() {
        ordersPresenter.getUserOrders(putOrderListIntoBundle(orderList));

        verify(mockView).setOrderList(new ArrayList<>(orderList));
    }

    @Test
    public void testOnError() {
        Throwable throwable = new Throwable();
        doAnswer(invocation -> Observable.error(throwable))
                .when(model)
                .getUserOrders(TestConst.TEST_USER_ID_WITH_ORDERS);

        ordersPresenter.onCreate(getArgs(Const.FRAGMENT_ACTIVE));
        ordersPresenter.getUserOrders();

        verify(mockView).showError(throwable);
    }

    @Test
    public void testOnCreate() {
        ordersPresenter.onCreate(getArgs(Const.FRAGMENT_ACTIVE));
        assertEquals(true, ordersPresenter.getUserOrderPredicate() instanceof ActiveOrderPredicate);

        ordersPresenter.onCreate(getArgs(Const.FRAGMENT_ARCHIVE));
        assertEquals(true, ordersPresenter.getUserOrderPredicate() instanceof OtherOrdersPredicate);
    }

    @Test
    public void testOnCreateNullBundle() {
        try
        {
            ordersPresenter.onCreate(null);
            fail();
        } catch (IllegalStateException expected) {
            assertEquals("Fragment type needs to be passed!", expected.getMessage());
        }
    }

    @Test
    public void testOnSaveInstanceState() {
        Bundle bundle = Bundle.EMPTY;
        ordersPresenter.onSaveInstanceState(bundle);
        assertEquals(Bundle.EMPTY, bundle);
    }

    @SuppressWarnings("unchecked")
    private List<UserOrder> getOrderListFromBundle(Bundle bundle) {
        return (List<UserOrder>) bundle.getSerializable(Const.SAVED_USER_ORDERS_KEY);
    }

    private Bundle putOrderListIntoBundle(List<UserOrder> orders) {
        Bundle bundle = new Bundle();
        ArrayList<UserOrder> userOrderArrayList = new ArrayList<>(orders);
        bundle.putSerializable(Const.SAVED_USER_ORDERS_KEY, userOrderArrayList);
        return bundle;
    }


    private Bundle getArgs(int value) {
        Bundle args = new Bundle();
        args.putInt(Const.FRAGMENT_KEY, value);
        return args;
    }


    @SuppressWarnings("unchecked")
    private List<UserOrder> filterOrders(List<UserOrder> userOrders, Predicate<UserOrder> userOrderPredicate){
        return Observable.just(userOrders)
                .flatMap(Observable::from)
                .filter(userOrderPredicate::apply)
                .toSortedList((userOrder, userOrder2) -> userOrder.getDepartureAt().compareTo(userOrder2.getDepartureAt()))
                .toBlocking()
                .first();
    }
}
