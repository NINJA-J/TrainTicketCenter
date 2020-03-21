package com.icss.Entity;

import java.util.Date;

public class Schedule extends ScheduleKey {
    private Date departure;

    private Date duration;

    private String arrival;

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival == null ? null : arrival.trim();
    }
}