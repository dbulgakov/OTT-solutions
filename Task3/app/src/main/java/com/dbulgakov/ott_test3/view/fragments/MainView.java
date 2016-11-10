package com.dbulgakov.ott_test3.view.fragments;

import com.dbulgakov.itemranger.Range;

import java.util.List;

public interface MainView extends View{
    void startPriceRangesDialog(List<Range> rangeList);
    void startProgressBar();
    void stopProgressBar();
}
