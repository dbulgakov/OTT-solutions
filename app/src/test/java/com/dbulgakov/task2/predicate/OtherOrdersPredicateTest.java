package com.dbulgakov.task2.predicate;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OtherOrdersPredicateTest extends PredicateTest{
    @Before
    public void setUp() throws Exception {
        super.setUp();
        setOrderPredicate(new OtherOrdersPredicate());
    }


    @Test
    public void testGetActiveOrder() {
        userOrder.setArrivalAt(getCurrentDatePlusDay());
        assertEquals(false, orderPredicate.apply(userOrder));
    }

    @Test
    public void testGetOldOrder() {
        userOrder.setArrivalAt(getOldDate());
        assertEquals(true, orderPredicate.apply(userOrder));
    }

    @Test
    public void testGetCancelledOrder(){
        userOrder.setUserCancel(true);
        userOrder.setArrivalAt(getCurrentDatePlusDay());
        assertEquals(true, orderPredicate.apply(userOrder));
    }
}
