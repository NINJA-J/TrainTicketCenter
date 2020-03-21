package com.icss.Entity;

public class Seat extends SeatKey {
    private Integer occupation;

    private Integer type;

    public Integer getOccupation() {
        return occupation;
    }

    public void setOccupation(Integer occupation) {
        this.occupation = occupation;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}