package com.dbulgakov.task2.model;

import com.dbulgakov.task2.model.api.ApiInterface;
import com.dbulgakov.task2.model.pojo.UserOrder;
import com.dbulgakov.task2.other.App;
import com.dbulgakov.task2.other.Const;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

public class ModelImpl implements Model{

    private final Observable.Transformer schedulersTransformer;

    @Inject
    ApiInterface apiInterface;

    @Inject
    @Named(Const.UI_THREAD)
    Scheduler uiThread;

    @Inject
    @Named(Const.IO_THREAD)
    Scheduler ioThread;

    public ModelImpl() {
        App.getComponent().inject(this);
        schedulersTransformer = o -> ((Observable) o).subscribeOn(ioThread)
                .observeOn(uiThread);
    }

    @Override
    public Observable<List<UserOrder>> getUserOrders(int userId) {
        return apiInterface
                .getUserOrders(userId)
                .compose(applySchedulers());
    }

    @SuppressWarnings("unchecked")
    private <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) schedulersTransformer;
    }
}
