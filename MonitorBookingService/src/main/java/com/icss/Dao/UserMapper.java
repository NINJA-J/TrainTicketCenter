package com.icss.Dao;

import com.icss.Entity.User;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);
}