package com.dbulgakov.task2.predicate;

import com.dbulgakov.task2.model.pojo.UserOrder;
import com.google.common.base.Predicate;

import java.util.Date;

/**
 * Если видимость класса должна быть package, то лучше об этом писать.
 * Например, гуглы (да и мы в проете тоже) делают так:
 */
/* package */ abstract class BaseOrderPredicate implements Predicate<UserOrder> {

    private Date currentDate;

    BaseOrderPredicate() {
        currentDate = new Date();
    }

    Date getCurrentDate(){
        return currentDate;
    }
}
