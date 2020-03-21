package com.icss.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class BanCi {
    private int Bno;
    private Date arrCusDepTime;
    private Map<Integer, List<Seat>> seatInfo;

    public Map<Integer, List<Seat>> getSeatInfo() {
        return this.seatInfo;
    }

    public void setSeatInfo(Map<Integer, List<Seat>> seatInfo) {
        this.seatInfo = seatInfo;
    }

    public int getBno() {
        return this.Bno;
    }

    public void setBno(int bno) {
        this.Bno = bno;
    }

    public Date getArrCusDepTime() {
        return this.arrCusDepTime;
    }

    public void setArrCusDepTime(Date arrCusDepTime) {
        this.arrCusDepTime = arrCusDepTime;
    }

    @Override
    public String toString(){
        return "{ bno=" + Bno + ", arrCusDepTime=" + arrCusDepTime + ", Map=" + getSeatInfo() + " }";
    }
}
