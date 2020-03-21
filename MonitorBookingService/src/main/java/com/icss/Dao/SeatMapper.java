package com.icss.Dao;

import com.icss.Entity.Seat;
import com.icss.Entity.SeatKey;

import java.util.List;
import java.util.Map;

public interface SeatMapper {
    int deleteByPrimaryKey(SeatKey key);

    int insert(Seat record);

    int insertSelective(Seat record);

    Seat selectByPrimaryKey(SeatKey key);

    int updateByPrimaryKeySelective(Seat record);

    int updateByPrimaryKey(Seat record);

    //

    List<Seat> getValidSeats(Map map);
    Seat getSeatByKey(SeatKey seatKey);
}