package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    void insert(User user);

    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);
}
