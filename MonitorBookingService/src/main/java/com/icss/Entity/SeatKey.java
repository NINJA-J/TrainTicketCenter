package com.icss.Entity;

public class SeatKey {
    private String train;

    private String seat;

    private Integer schedule;

    public String getTrain() {
        return train;
    }

    public void setTrain(String train) {
        this.train = train == null ? null : train.trim();
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat == null ? null : seat.trim();
    }

    public Integer getSchedule() {
        return schedule;
    }

    public void setSchedule(Integer schedule) {
        this.schedule = schedule;
    }
}