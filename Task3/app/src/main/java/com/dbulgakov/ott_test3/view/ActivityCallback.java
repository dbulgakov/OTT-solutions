package com.dbulgakov.ott_test3.view;

import com.dbulgakov.itemranger.Range;

import java.util.List;

public interface ActivityCallback {
    void onStartHotelPricesRangesDialogFragment(List<Range> rangeList);
    void onDialogRangesSelected(int rangeCount);
}
