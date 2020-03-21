package com.icss.Dao;

import com.icss.Entity.Schedule;
import com.icss.Entity.ScheduleKey;

public interface ScheduleMapper {
    int deleteByPrimaryKey(ScheduleKey key);

    int insert(Schedule record);

    int insertSelective(Schedule record);

    Schedule selectByPrimaryKey(ScheduleKey key);

    int updateByPrimaryKeySelective(Schedule record);

    int updateByPrimaryKey(Schedule record);
}