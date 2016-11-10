package com.dbulgakov.itemranger;

import java.util.Locale;

public class Range {

    public Range(int startValue, int endValue, int elementNumber) {
        this.startValue = startValue;
        this.endValue = endValue;
        this.elementNumber = elementNumber;
    }

    private int startValue;
    private int endValue;
    private int elementNumber;

    public int getStartValue() {
        return startValue;
    }

    public void setStartValue(int startValue) {
        this.startValue = startValue;
    }

    public int getEndValue() {
        return endValue;
    }

    public void setEndValue(int endValue) {
        this.endValue = endValue;
    }

    public int getElementNumber() {
        return elementNumber;
    }

    public void setElementNumber(int elementNumber) {
        this.elementNumber = elementNumber;
    }

    @Override
    public String toString() {
        if (endValue != Integer.MAX_VALUE) {
            return String.format(Locale.getDefault(), "%d - %d", startValue, endValue);
        } else
            return startValue + "+";
    }
}