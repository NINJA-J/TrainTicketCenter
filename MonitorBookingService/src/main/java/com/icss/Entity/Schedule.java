package com.icss.Entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import org.springframework.jms.connection.CachingConnectionFactory;

@Getter
@Setter
public class Schedule extends ScheduleKey {
    private Date departure;

    private Date duration;

    private String arrival;
}