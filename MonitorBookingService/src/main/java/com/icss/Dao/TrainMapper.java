package com.icss.Dao;

import com.icss.Entity.Train;

public interface TrainMapper {
    int deleteByPrimaryKey(String name);

    int insert(Train record);

    int insertSelective(Train record);

    Train selectByPrimaryKey(String name);

    int updateByPrimaryKeySelective(Train record);

    int updateByPrimaryKeyWithBLOBs(Train record);

    int updateByPrimaryKey(Train record);
}