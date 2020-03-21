package com.icss.Entity;

public class Ticket {
    public static final int STATE_INITIALIZED   = 0;
    public static final int STATE_REGISTERED    = 1;
    public static final int STATE_ISSUED        = 2;
    public static final int STATE_CANCELED      = 3;

    private Integer id;

    private String train;

    private String seat;

    private Integer schedule;

    private String state;

    private String owner;

    private Integer departure;

    private Integer arrival;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    public Integer getDeparture() {
        return departure;
    }

    public void setDeparture(Integer departure) {
        this.departure = departure;
    }

    public Integer getArrival() {
        return arrival;
    }

    public void setArrival(Integer arrival) {
        this.arrival = arrival;
    }

    public ScheduleKey getScheduleKey(){
        ScheduleKey scheduleKey = new ScheduleKey();
        scheduleKey.setId(getSchedule());
        scheduleKey.setTrain(getTrain());

        return scheduleKey;
    }
}