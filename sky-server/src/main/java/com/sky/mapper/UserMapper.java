package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

public interface UserMapper {

    void insert(User user);

    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);

    @Select("select * from user where id = #{userId}")
    User getById(Long userId);

    Integer countByMap(Map map);
}
