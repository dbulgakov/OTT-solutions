package com.dbulgakov.itemranger.other;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.dbulgakov.itemranger.interfaces.RangeAble;

public class TestRangeAble implements RangeAble{

    int value;

    public TestRangeAble(int value) {
        this.value = value;
    }

    @Override
    public int getValueToRangeBy() {
        return value;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int compareTo(RangeAble rangeAble) {
         return Integer.compare(getValueToRangeBy(), rangeAble.getValueToRangeBy());
    }
}
