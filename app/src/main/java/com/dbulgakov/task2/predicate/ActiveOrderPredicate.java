package com.dbulgakov.task2.predicate;


import com.dbulgakov.task2.model.pojo.UserOrder;

public class ActiveOrderPredicate extends BaseOrderPredicate {

    public ActiveOrderPredicate(){
        super();
    }

    @Override
    public boolean apply(UserOrder userOrder) {
        return userOrder.getArrivalAt().after(getCurrentDate()) && !userOrder.getUserCancel();
    }
}
