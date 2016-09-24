package com.dbulgakov.task2.predicate;

import com.dbulgakov.task2.model.pojo.UserOrder;
import com.google.common.base.Predicate;

import java.util.Date;

abstract class BaseOrderPredicate implements Predicate<UserOrder> {

    private Date currentDate;

    BaseOrderPredicate() {
        currentDate = new Date();
    }

    Date getCurrentDate(){
        return currentDate;
    }
}
