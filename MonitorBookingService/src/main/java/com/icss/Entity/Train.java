package com.icss.Entity;

public class Train {
    private String name;

    private Integer departure;

    private Integer destination;

    private String stations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getDeparture() {
        return departure;
    }

    public void setDeparture(Integer departure) {
        this.departure = departure;
    }

    public Integer getDestination() {
        return destination;
    }

    public void setDestination(Integer destination) {
        this.destination = destination;
    }

    public String getStations() {
        return stations;
    }

    public void setStations(String stations) {
        this.stations = stations == null ? null : stations.trim();
    }
}