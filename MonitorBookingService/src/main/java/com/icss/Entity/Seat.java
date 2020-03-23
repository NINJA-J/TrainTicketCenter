package com.icss.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Seat extends SeatKey {
    private Integer occupation;

    private Integer type;
}