package com.sky.mapper;

import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface EmployeeMapper {

    // 根据用户名查询员工
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    // 插入新用户
    void insert(Employee employee);
}
