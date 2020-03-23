package com.icss.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatKey {
    private String train;

    private String seat;

    private Integer schedule;
}