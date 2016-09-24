package com.dbulgakov.task2.predicate;

import com.dbulgakov.task2.model.pojo.UserOrder;

public class OtherOrdersPredicate extends BaseOrderPredicate {

    public OtherOrdersPredicate(){
        super();
    }

    @Override
    public boolean apply(UserOrder userOrder) {
        return userOrder.getArrivalAt().before(getCurrentDate()) || userOrder.getUserCancel();
    }
}
