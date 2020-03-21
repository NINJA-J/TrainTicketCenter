package com.icss.entity;

public class Seat {
    private int Bno;
    private String SeatNo;
    private int Occupation;
    private int type;

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getBno() {
        return this.Bno;
    }

    public void setBno(int bno) {
        this.Bno = bno;
    }

    public String getSeatNo() {
        return this.SeatNo;
    }

    public void setSeatNo(String seatNo) {
        this.SeatNo = seatNo;
    }

    public int getOccupation() {
        return this.Occupation;
    }

    public void setOccupation(int occupation) {
        this.Occupation = occupation;
    }

    @Override
    public String toString(){
        return "{ SeatNo=" + SeatNo + ", Occupation=" + Occupation + ", Type=" + type + " }";
    }
}
