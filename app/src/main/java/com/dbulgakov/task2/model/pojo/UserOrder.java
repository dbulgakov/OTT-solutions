package com.dbulgakov.task2.model.pojo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;


public class UserOrder {

    @SerializedName("order_date")
    @Expose
    private Date orderDate;
    @SerializedName("departure_at")
    @Expose
    private Date departureAt;
    @SerializedName("arrival_at")
    @Expose
    private Date arrivalAt;
    @SerializedName("stop_number")
    @Expose
    private int stopNumber;
    @SerializedName("flight_duration")
    @Expose
    private double flightDuration;
    @SerializedName("airline")
    @Expose
    private String airline;
    @SerializedName("flight_number")
    @Expose
    private int flightNumber;
    @SerializedName("origin")
    @Expose
    private String origin;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("user_cancel")
    @Expose
    private boolean userCancel;

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDepartureAt() {
        return departureAt;
    }

    public void setDepartureAt(Date departureAt) {
        this.departureAt = departureAt;
    }

    public Date getArrivalAt() {
        return arrivalAt;
    }

    public void setArrivalAt(Date arrivalAt) {
        this.arrivalAt = arrivalAt;
    }

    public int getStopNumber() {
        return stopNumber;
    }

    public void setStopNumber(int stopNumber) {
        this.stopNumber = stopNumber;
    }

    public double getFlightDuration() {
        return flightDuration;
    }

    public void setFlightDuration(double flightDuration) {
        this.flightDuration = flightDuration;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public boolean getUserCancel() {
        return userCancel;
    }

    public void setUserCancel(boolean userCancel) {
        this.userCancel = userCancel;
    }

}
