package com.dbulgakov.ott_test3.model.DTO;

import android.os.Build;
import android.support.annotation.NonNull;

import com.dbulgakov.itemranger.interfaces.RangeAble;
import com.google.gson.annotations.SerializedName;

public class HotelInfo implements RangeAble {
    @SerializedName("id")
    private int hotelId;

    @SerializedName("hotel_name")
    private String name;

    @SerializedName("price")
    private int price;

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int getValueToRangeBy() {
        return getPrice();
    }

    @Override
    public int compareTo(@NonNull RangeAble o) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Integer.compare(getValueToRangeBy(), o.getValueToRangeBy());
        } else {
            return Integer.valueOf(getValueToRangeBy()).compareTo(o.getValueToRangeBy());
        }
    }
}
