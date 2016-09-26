package com.dbulgakov.task2.predicate;

import com.dbulgakov.task2.model.pojo.UserOrder;
import com.dbulgakov.task2.other.BaseTest;
import com.google.common.base.Predicate;

import org.junit.Before;
import org.junit.Ignore;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Ignore
public class PredicateTest extends BaseTest {

    UserOrder userOrder;
    Predicate<UserOrder> orderPredicate;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        userOrder = new UserOrder();
        userOrder.setUserCancel(false);
    }

    Date getCurrentDatePlusDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

    Date getOldDate(){
        return new GregorianCalendar(2013,0,31).getTime();
    }

    void setOrderPredicate(Predicate<UserOrder> predicate) {
        orderPredicate = predicate;
    }
}
