package com.icss.Service;

import com.icss.Dao.SeatMapper;
import com.icss.Entity.ScheduleKey;
import com.icss.Entity.Seat;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeatSerivce {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public List<Seat> getValidSeats(ScheduleKey scheduleKey, int occupation){
        Map<String, Object> cond = new HashMap<String, Object>();
        cond.put("train", scheduleKey.getTrain());
        cond.put("schedule", scheduleKey.getId());
        cond.put("occupation", occupation);

        SqlSession sqlSession = sqlSessionFactory.openSession();
        SeatMapper seatMapper = sqlSession.getMapper(SeatMapper.class);
        return seatMapper.getValidSeats(cond);
    }
}
