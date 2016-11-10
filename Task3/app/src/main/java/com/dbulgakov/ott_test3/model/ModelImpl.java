package com.dbulgakov.ott_test3.model;

import com.dbulgakov.itemranger.ItemRanger;
import com.dbulgakov.itemranger.Range;
import com.dbulgakov.ott_test3.R;
import com.dbulgakov.ott_test3.model.DTO.HotelInfo;
import com.dbulgakov.ott_test3.model.api.ApiInterface;
import com.dbulgakov.ott_test3.other.App;
import com.dbulgakov.ott_test3.other.Const;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;

public class ModelImpl implements Model{

    private final Observable.Transformer schedulersTransformer;
    private final List<Integer> RANGES_LIST;

    @Inject
    ApiInterface apiInterface;

    @Inject
    Gson gson;

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
        RANGES_LIST = parseRangesFile();
    }

    @Override
    public Observable<List<HotelInfo>> getAvailableHotels(int cityId) {
        return apiInterface
                .getHotels(cityId)
                .compose(applySchedulers());
    }

    @Override
    public Observable<List<Range>> getCommonPriceRanges(int cityId) {
        return apiInterface
                .getHotels(cityId)
                .compose(applySchedulers())
                .map(hotels -> new ItemRanger(RANGES_LIST, Const.MAX_HOTEL_RANGE_NUMBER).getSelectedRanges(new ArrayList<>(hotels)));
    }


    @SuppressWarnings("unchecked")
    private <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) schedulersTransformer;
    }

    private List<Integer> parseRangesFile(){
        InputStream raw = App.getContext().getResources().openRawResource(R.raw.ranges);
        Reader rd = new BufferedReader(new InputStreamReader(raw));
        Type listType = new TypeToken<List<Integer>>() {}.getType();
        return gson.fromJson(rd, listType);
    }
}
