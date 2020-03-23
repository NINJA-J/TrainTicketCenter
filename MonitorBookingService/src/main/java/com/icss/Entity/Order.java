package com.icss.Entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Order implements Serializable {
    public static final int OP_BUY      = 0;
    public static final int OP_CANCEL   = 1;

    private int op;

    private Ticket ticket;

    @Override
    public String toString() {
        return "Order{" +
                "op=" + op +
                ", " + ticket +
                '}';
    }
}
