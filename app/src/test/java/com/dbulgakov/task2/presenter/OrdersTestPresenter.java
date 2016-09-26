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
import org.junit.Test;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
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
    public void testLoadActiveUserOrders() {
        ordersPresenter.onCreate(getArgs(Const.FRAGMENT_ACTIVE));
        ordersPresenter.getUserOrders();
        ordersPresenter.onStop();

        verify(mockView).setOrderList(filterOrders(orderList, new ActiveOrderPredicate()));
    }

    @Test
    public void testLoadOtherUserOrders() {
        ordersPresenter.onCreate(getArgs(Const.FRAGMENT_ARCHIVE));
        ordersPresenter.getUserOrders();
        ordersPresenter.onStop();

        verify(mockView).setOrderList(filterOrders(orderList, new OtherOrdersPredicate()));
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
