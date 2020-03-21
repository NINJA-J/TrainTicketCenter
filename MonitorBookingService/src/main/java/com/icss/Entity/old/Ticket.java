package com.icss.Entity.old;

public class Ticket {
    private String orderNo;
    private String owner;

    private int bno;
    private String seatNo;
    private String stype;
    private int sAreaIndex;
    private int eAreaIndex;

    private int state;

    public int getBno() {
        return this.bno;
    }

    public void setBno(int bno) {
        this.bno = bno;
    }

    public String getSeatNo() {
        return this.seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public String getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStype() {
        return this.stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getsAreaIndex() {
        return this.sAreaIndex;
    }

    public void setsAreaIndex(int sAreaIndex) {
        this.sAreaIndex = sAreaIndex;
    }

    public int geteAreaIndex() {
        return this.eAreaIndex;
    }

    public void seteAreaIndex(int eAreaIndex) {
        this.eAreaIndex = eAreaIndex;
    }
}
