package com.dbulgakov.task2.predicate;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ActiveOrderPredicateTest extends PredicateTest{
    @Before
    public void setUp() throws Exception {
        super.setUp();
        setOrderPredicate(new ActiveOrderPredicate());
    }


    @Test
    public void testGetActiveOrder() {
        userOrder.setArrivalAt(getCurrentDatePlusDay());
        assertEquals(true, orderPredicate.apply(userOrder));
    }

    @Test
    public void testGetOldOrder() {
        userOrder.setArrivalAt(getOldDate());
        assertEquals(false, orderPredicate.apply(userOrder));
    }

    @Test
    public void testGetCancelledOrder(){
        userOrder.setUserCancel(true);
        userOrder.setArrivalAt(getCurrentDatePlusDay());
        assertEquals(false, orderPredicate.apply(userOrder));
    }
}
