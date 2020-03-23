package com.icss.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.icss.Service.TicketService;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ticket implements Serializable {
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

    @JsonIgnore
    public ScheduleKey getScheduleKey(){
        ScheduleKey scheduleKey = new ScheduleKey();
        scheduleKey.setId(schedule);
        scheduleKey.setTrain(train);

        return scheduleKey;
    }

    @JsonIgnore
    public int getOccupation(){
        return TicketService.GenerateCover(departure, arrival);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", train='" + train + '\'' +
                ", seat='" + seat + '\'' +
                ", schedule=" + schedule +
                ", state='" + state + '\'' +
                ", owner='" + owner + '\'' +
                ", departure=" + departure +
                ", arrival=" + arrival +
                '}';
    }

    @Override
    public boolean equals(Object other){
        return this.toString().equals(other.toString());
    }
}