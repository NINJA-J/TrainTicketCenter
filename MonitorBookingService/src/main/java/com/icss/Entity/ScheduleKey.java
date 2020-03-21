package com.icss.Entity;

public class ScheduleKey {
    private Integer id;

    private String train;

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
}