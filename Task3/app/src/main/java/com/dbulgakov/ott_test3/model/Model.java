package com.dbulgakov.ott_test3.model;

import com.dbulgakov.itemranger.Range;
import com.dbulgakov.ott_test3.model.DTO.HotelInfo;

import java.util.List;

import rx.Observable;

public interface Model {
    Observable<List<HotelInfo>> getAvailableHotels(int cityId);
    Observable<List<Range>> getCommonPriceRanges(int cityId);
}
