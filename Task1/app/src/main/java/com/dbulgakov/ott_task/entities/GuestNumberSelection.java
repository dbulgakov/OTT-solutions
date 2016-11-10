package com.dbulgakov.ott_task.entities;

import android.os.Parcel;
import android.os.Parcelable;


public class GuestNumberSelection implements Parcelable{
    private int adultsNumber;
    private int teensNumber;
    private int childrenNumber;


    public GuestNumberSelection(int adultsNumber, int teensNumber, int childrenNumber) {
        this.adultsNumber = adultsNumber;
        this.teensNumber = teensNumber;
        this.childrenNumber = childrenNumber;
    }

    public GuestNumberSelection (Parcel in) {
        int[] data = new int[3];

        in.readIntArray(data);

        adultsNumber = data[0];
        teensNumber = data[1];
        childrenNumber = data[2];
    }


    public int getAdultsNumber() {
        return adultsNumber;
    }

    public void setAdultsNumber(int adultsNumber) {
        this.adultsNumber = adultsNumber;
    }

    public int getTeensNumber() {
        return teensNumber;
    }

    public void setTeensNumber(int teensNumber) {
        this.teensNumber = teensNumber;
    }

    public int getChildrenNumber() {
        return childrenNumber;
    }

    public void setChildrenNumber(int childrenNumber) {
        this.childrenNumber = childrenNumber;
    }


    // code to implement Parcelable

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeIntArray(new int[] {adultsNumber, teensNumber, childrenNumber});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public GuestNumberSelection createFromParcel(Parcel in) {
            return new GuestNumberSelection(in);
        }

        public GuestNumberSelection[] newArray(int size) {
            return new GuestNumberSelection[size];
        }
    };
}
