package com.icss.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class Train {
    private String name;

    private Integer departure;

    private Integer destination;

    private String stations;

    private List<Integer> stationList;
}