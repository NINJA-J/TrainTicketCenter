package com.icss.Entity.old;

import oracle.sql.INTERVALDS;

public class Station {
    private int area;
    private int areaIndex;
    private String aName;
    private String name;
    private String tno;

    private INTERVALDS interval;

    public INTERVALDS getInterval() {
        return this.interval;
    }

    public void setInterval(INTERVALDS interval) {
        this.interval = interval;
    }

    public int getArea() {
        return this.area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getAreaIndex() {
        return this.areaIndex;
    }

    public void setAreaIndex(int areaIndex) {
        this.areaIndex = areaIndex;
    }

    public String getaName() {
        return this.aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTno() {
        return this.tno;
    }

    public void setTno(String tno) {
        this.tno = tno;
    }
}
